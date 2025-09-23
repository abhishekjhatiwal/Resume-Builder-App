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
        color = android.graphics.Color.BLACK
    }
    val headerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12f
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.BOLD
        )
        color = android.graphics.Color.BLACK
    }
    val normalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 11f
        typeface = android.graphics.Typeface.create(
            android.graphics.Typeface.DEFAULT,
            android.graphics.Typeface.NORMAL
        )
        color = android.graphics.Color.BLACK
    }
    val divider = Paint().apply {
        color = android.graphics.Color.BLACK
        strokeWidth = 1f
    }

    var y = 40f

    // Photo handling
    photo?.let {
        try {
            val safeBitmap = it.convertToSoftwareBitmap()
            val size = 96
            val scaled = safeBitmap.scale(size, size, false)
            canvas.drawBitmap(scaled, (pageWidth - size - 40).toFloat(), y, null)
        } catch (e: Exception) {
            // Handle bitmap scaling error gracefully
            e.printStackTrace()
        }
    }

    // Personal Information Header
    canvas.drawText(resume.personal.name, 40f, y + 24f, titlePaint)
    y += 50f

    canvas.drawText("Email: ${resume.personal.email}", 40f, y, normalPaint)
    y += 20f
    canvas.drawText("Phone: ${resume.personal.phone}", 40f, y, normalPaint)
    y += 20f
    canvas.drawText("Address: ${resume.personal.address}", 40f, y, normalPaint)
    y += 30f

    // Education Section
    canvas.drawLine(40f, y, (pageWidth - 40).toFloat(), y, divider)
    y += 20f

    canvas.drawText("EDUCATION", 40f, y, headerPaint)
    y += 25f

    canvas.drawText("${resume.qualificationsSkills.degree}", 40f, y, normalPaint)
    y += 18f

    canvas.drawText("${resume.qualificationsSkills.institute}", 40f, y, normalPaint)
    y += 18f

    canvas.drawText("Grade: ${resume.qualificationsSkills.grade}", 40f, y, normalPaint)
    y += 18f

    canvas.drawText(
        "${resume.qualificationsSkills.startDate} - ${resume.qualificationsSkills.endDate}",
        40f,
        y,
        normalPaint
    )
    y += 25f

    // Skills Section
    canvas.drawLine(40f, y, (pageWidth - 40).toFloat(), y, divider)
    y += 20f

    canvas.drawText("SKILLS", 40f, y, headerPaint)
    y += 25f

    if (resume.qualificationsSkills.skills.isNotEmpty()) {
        y = drawMultiLine(
            canvas,
            resume.qualificationsSkills.skills,
            40f,
            y,
            (pageWidth - 80),
            normalPaint
        )
        y += 15f
    }

    // Experience Section
    canvas.drawLine(40f, y, (pageWidth - 40).toFloat(), y, divider)
    y += 20f

    canvas.drawText("EXPERIENCE", 40f, y, headerPaint)
    y += 25f

    if (resume.experience.company.isNotEmpty() || resume.experience.position.isNotEmpty()) {
        canvas.drawText(
            "${resume.experience.position} at ${resume.experience.company}",
            40f,
            y,
            normalPaint
        )
        y += 18f

        if (resume.experience.startDate.isNotEmpty() || resume.experience.endDate.isNotEmpty()) {
            canvas.drawText(
                "${resume.experience.startDate} - ${resume.experience.endDate}",
                40f,
                y,
                normalPaint
            )
            y += 18f
        }

        if (resume.experience.description.isNotEmpty()) {
            y = drawMultiLine(
                canvas,
                resume.experience.description,
                40f,
                y,
                (pageWidth - 80),
                normalPaint
            )
        }
    }

    pdf.finishPage(page)

    try {
        context.contentResolver.openOutputStream(outputUri)?.use { out ->
            pdf.writeTo(out)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    } finally {
        pdf.close()
    }
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
                y += 18f // Increased line spacing
            }
            line = word
        } else {
            line = trial
        }
    }

    if (line.isNotEmpty()) {
        canvas.drawText(line, x, y, paint)
        y += 18f
    }

    return y
}

// Extension function to handle bitmap conversion safely
@RequiresApi(Build.VERSION_CODES.O)
private fun Bitmap.convertToSoftwareBitmap(): Bitmap {
    return if (this.config == Bitmap.Config.HARDWARE) {
        this.copy(Bitmap.Config.ARGB_8888, false)
    } else {
        this
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