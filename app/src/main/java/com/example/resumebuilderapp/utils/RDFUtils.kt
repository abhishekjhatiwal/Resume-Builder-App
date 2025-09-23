package com.example.resumebuilderapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.resumebuilderapp.data.ResumeData
import androidx.core.graphics.scale
import java.io.IOException

//@RequiresApi(Build.VERSION_CODES.KITKAT)
@RequiresApi(Build.VERSION_CODES.O)
fun generateResumePDFToURI(
    context: Context,
    resume: ResumeData,
    photo: Bitmap?,
    outputUri: Uri,
) {
    var pdf: PdfDocument? = null
    var page: PdfDocument.Page? = null

    try {
        val pageWidth = 595
        val pageHeight = 842    // A4 Size

        pdf = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        page = pdf.startPage(pageInfo)
        val canvas = page.canvas

        // Initialize paints with proper settings
        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 20f
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
            color = android.graphics.Color.BLACK
        }

        val headerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 14f
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
            color = android.graphics.Color.BLACK
        }

        val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 12f
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.NORMAL
            )
            color = android.graphics.Color.BLACK
        }

        val dividerPaint = Paint().apply {
            color = android.graphics.Color.GRAY
            strokeWidth = 1f
        }

        var currentY = 50f
        val leftMargin = 50f
        val rightMargin = pageWidth - 50f
        val contentWidth = rightMargin - leftMargin

        // Draw photo if available
        photo?.let { bitmap ->
            try {
                val safeBitmap = convertBitmapToSoftware(bitmap)
                val photoSize = 80f
                val photoX = rightMargin - photoSize
                val scaledPhoto = safeBitmap.scale(photoSize.toInt(), photoSize.toInt(), false)
                canvas.drawBitmap(scaledPhoto, photoX, currentY, null)
            } catch (e: Exception) {
                // Continue without photo if there's an error
            }
        }

        // Personal Information
        if (resume.personal.name.isNotBlank()) {
            canvas.drawText(resume.personal.name, leftMargin, currentY + 20f, titlePaint)
            currentY += 35f
        }

        if (resume.personal.email.isNotBlank()) {
            canvas.drawText("Email: ${resume.personal.email}", leftMargin, currentY, normalPaint)
            currentY += 20f
        }

        if (resume.personal.phone.isNotBlank()) {
            canvas.drawText("Phone: ${resume.personal.phone}", leftMargin, currentY, normalPaint)
            currentY += 20f
        }

        if (resume.personal.address.isNotBlank()) {
            canvas.drawText("Address: ${resume.personal.address}", leftMargin, currentY, normalPaint)
            currentY += 30f
        }

        // Check if we need to add education section
        val hasEducation = resume.qualificationsSkills.degree.isNotBlank() ||
                resume.qualificationsSkills.institute.isNotBlank()

        if (hasEducation) {
            // Education Section
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dividerPaint)
            currentY += 25f

            canvas.drawText("EDUCATION", leftMargin, currentY, headerPaint)
            currentY += 25f

            if (resume.qualificationsSkills.degree.isNotBlank()) {
                canvas.drawText(resume.qualificationsSkills.degree, leftMargin, currentY, normalPaint)
                currentY += 18f
            }

            if (resume.qualificationsSkills.institute.isNotBlank()) {
                canvas.drawText(resume.qualificationsSkills.institute, leftMargin, currentY, normalPaint)
                currentY += 18f
            }

            if (resume.qualificationsSkills.grade.isNotBlank()) {
                canvas.drawText("Grade: ${resume.qualificationsSkills.grade}", leftMargin, currentY, normalPaint)
                currentY += 18f
            }

            val startDate = resume.qualificationsSkills.startDate.takeIf { it.isNotBlank() } ?: ""
            val endDate = resume.qualificationsSkills.endDate.takeIf { it.isNotBlank() } ?: ""
            if (startDate.isNotEmpty() || endDate.isNotEmpty()) {
                val duration = when {
                    startDate.isNotEmpty() && endDate.isNotEmpty() -> "$startDate - $endDate"
                    startDate.isNotEmpty() -> "From $startDate"
                    endDate.isNotEmpty() -> "Until $endDate"
                    else -> ""
                }
                if (duration.isNotEmpty()) {
                    canvas.drawText(duration, leftMargin, currentY, normalPaint)
                    currentY += 25f
                }
            } else {
                currentY += 25f
            }
        }

        // Skills Section
        if (resume.qualificationsSkills.skills.isNotBlank()) {
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dividerPaint)
            currentY += 25f

            canvas.drawText("SKILLS", leftMargin, currentY, headerPaint)
            currentY += 25f

            currentY = drawMultiLineText(
                canvas,
                resume.qualificationsSkills.skills,
                leftMargin,
                currentY,
                contentWidth.toInt(),
                normalPaint
            )
            currentY += 25f
        }

        // Experience Section
        val hasExperience = resume.experience.company.isNotBlank() ||
                resume.experience.position.isNotBlank() ||
                resume.experience.description.isNotBlank()

        if (hasExperience) {
            canvas.drawLine(leftMargin, currentY, rightMargin, currentY, dividerPaint)
            currentY += 25f

            canvas.drawText("EXPERIENCE", leftMargin, currentY, headerPaint)
            currentY += 25f

            // Position and Company
            val jobTitle = buildString {
                if (resume.experience.position.isNotBlank()) {
                    append(resume.experience.position)
                }
                if (resume.experience.company.isNotBlank()) {
                    if (isNotEmpty()) append(" at ")
                    append(resume.experience.company)
                }
            }

            if (jobTitle.isNotEmpty()) {
                canvas.drawText(jobTitle, leftMargin, currentY, normalPaint)
                currentY += 18f
            }

            // Experience Duration
            val expStartDate = resume.experience.startDate.takeIf { it.isNotBlank() } ?: ""
            val expEndDate = resume.experience.endDate.takeIf { it.isNotBlank() } ?: ""
            if (expStartDate.isNotEmpty() || expEndDate.isNotEmpty()) {
                val expDuration = when {
                    expStartDate.isNotEmpty() && expEndDate.isNotEmpty() -> "$expStartDate - $expEndDate"
                    expStartDate.isNotEmpty() -> "From $expStartDate"
                    expEndDate.isNotEmpty() -> "Until $expEndDate"
                    else -> ""
                }
                if (expDuration.isNotEmpty()) {
                    canvas.drawText(expDuration, leftMargin, currentY, normalPaint)
                    currentY += 18f
                }
            }

            // Description
            if (resume.experience.description.isNotBlank()) {
                currentY += 5f // Small gap before description
                currentY = drawMultiLineText(
                    canvas,
                    resume.experience.description,
                    leftMargin,
                    currentY,
                    contentWidth.toInt(),
                    normalPaint
                )
            }
        }

        // Finish the page
        pdf.finishPage(page)

        // Write to output stream
        context.contentResolver.openOutputStream(outputUri)?.use { outputStream ->
            pdf.writeTo(outputStream)
            outputStream.flush()
        } ?: throw IOException("Could not open output stream for URI: $outputUri")

    } catch (e: Exception) {
        throw IOException("Failed to generate PDF: ${e.message}", e)
    } finally {
        try {
            pdf?.close()
        } catch (e: Exception) {
            // Log but don't throw
        }
    }
}

private fun drawMultiLineText(
    canvas: Canvas,
    text: String,
    x: Float,
    startY: Float,
    maxWidth: Int,
    paint: Paint
): Float {
    if (text.isBlank()) return startY

    val words = text.trim().split(Regex("\\s+"))
    var currentY = startY
    var currentLine = ""

    for (word in words) {
        val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
        val testWidth = paint.measureText(testLine)

        if (testWidth <= maxWidth) {
            currentLine = testLine
        } else {
            // Draw the current line if it's not empty
            if (currentLine.isNotEmpty()) {
                canvas.drawText(currentLine, x, currentY, paint)
                currentY += 20f
            }
            currentLine = word
        }
    }

    // Draw the last line if it's not empty
    if (currentLine.isNotEmpty()) {
        canvas.drawText(currentLine, x, currentY, paint)
        currentY += 20f
    }

    return currentY
}

private fun convertBitmapToSoftware(bitmap: Bitmap): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
        bitmap.config == Bitmap.Config.HARDWARE) {
        bitmap.copy(Bitmap.Config.ARGB_8888, false)
    } else {
        bitmap
    }
}














































/*
//@RequiresApi(Build.VERSION_CODES.KITKAT)
@RequiresApi(Build.VERSION_CODES.O)
fun generateResumePDFToURI(
    context: Context,
    resume: ResumeData,
    photo: Bitmap?,
    outputUri: Uri,
) {
    val pageWidth = 595
    val pageHeight = 842    // A4 Size

    val pdf = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val page = pdf.startPage(pageInfo)
    val canvas = page.canvas

    val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20f
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.BOLD
        )
    }
    val headerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12f
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.BOLD
        )
    }
    val divider = Paint().apply {
        color = android.graphics.Color.BLACK
        strokeWidth = 1f
    }

    var y = 40f

    // Photo handling
    photo?.let {
        val safeBitmap = it.toSoftwareBitmap()
        val size = 96
        val scaled = safeBitmap.scale(size, size, false)
        canvas.drawBitmap(scaled, (pageWidth - size - 40).toFloat(), y, null)
    }

    // Personal Information
    canvas.drawText("Name: ${resume.personal.name}", 40f, y + 24f, titlePaint)
    y += 44f
    canvas.drawText("Email: ${resume.personal.email}", 40f, y, titlePaint)
    y += 18f
    canvas.drawText("Phone: ${resume.personal.phone}", 40f, y, titlePaint)
    y += 18f
    canvas.drawText("Address: ${resume.personal.address}", 40f, y, titlePaint)
    y += 44f

    // Divider
    canvas.drawLine(40f, y, (pageWidth - 40).toFloat(), y, divider)
    y += 24f

    // Qualifications (corrected spelling)
    canvas.drawText("Degree: ${resume.qualificationsSkills.degree}", 40f, y, headerPaint)
    y += 18f

    canvas.drawText("Institute: ${resume.qualificationsSkills.institute}", 40f, y, headerPaint)
    y += 18f

    canvas.drawText("Grade: ${resume.qualificationsSkills.grade}", 40f, y, headerPaint)
    y += 18f

    canvas.drawText(
        "Duration: ${resume.qualificationsSkills.startDate} - ${resume.qualificationsSkills.endDate}",
        40f,
        y,
        headerPaint
    )
    y += 18f

    // Divider
    canvas.drawLine(40f, y, (pageWidth - 40).toFloat(), y, divider)
    y += 24f

    // Skills
    canvas.drawText("Skills:", 40f, y, headerPaint)
    y += 20f

    if (resume.qualificationsSkills.skills.isNotEmpty()) {
        y = drawMultiLine(
            canvas,
            resume.qualificationsSkills.skills,
            40f,
            y,
            (pageWidth - 80),
            headerPaint
        )
        y += 6f
    }

    // Divider
    canvas.drawLine(40f, y, (pageWidth - 40).toFloat(), y, divider)
    y += 24f

    // Experience
    canvas.drawText("Experience", 40f, y, headerPaint)
    y += 18f

    canvas.drawText(
        "Company: ${resume.experience.company} | Position: ${resume.experience.position}",
        40f,
        y,
        headerPaint
    )
    y += 18f

    canvas.drawText(
        "Duration: ${resume.experience.startDate} - ${resume.experience.endDate}",
        40f,
        y,
        headerPaint
    )
    y += 18f

    canvas.drawText("Description: ${resume.experience.description}", 40f, y, headerPaint)

    pdf.finishPage(page)
    context.contentResolver.openOutputStream(outputUri)?.use { out ->
        pdf.writeTo(out)
    }
    pdf.close()
}

private fun drawMultiLine(
    canvas: Canvas,
    text: String,
    x: Float,
    startY: Float,
    maxWidth: Int,
    paint: Paint
): Float {
    val words = text.split(Regex("\\s+"))
    var y = startY
    var line = ""

    for (word in words) {
        val trial = if (line.isEmpty()) word else "$line $word"
        if (paint.measureText(trial) >= maxWidth) {
            if (line.isNotEmpty()) {
                canvas.drawText(line, x, y, paint)
                y += 16f
            }
            line = word
        } else {
            line = trial
        }
    }

    if (line.isNotEmpty()) {
        canvas.drawText(line, x, y, paint)
        y += 16f
    }

    return y
}


 */