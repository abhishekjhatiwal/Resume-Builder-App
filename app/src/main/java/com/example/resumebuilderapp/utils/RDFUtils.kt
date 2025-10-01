package com.example.resumebuilderapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.resumebuilderapp.data.ResumeData
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.KITKAT)
fun generateResumePDFToURI(
    context: Context,
    resume: ResumeData,
    photo: Bitmap?,
    outputUri: Uri
) {
    val pageWidth = 595
    val pageHeight = 842 // A4 size

    val pdf = PdfDocument()
    try {
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdf.startPage(pageInfo)
        val canvas = page.canvas

        // ---- Simple paints ----
        val titlePaint = Paint().apply {
            textSize = 20f
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
            color = android.graphics.Color.BLACK
        }
        val normalPaint = Paint().apply {
            textSize = 12f
            color = android.graphics.Color.BLACK
        }

        var y = 50f

        // ---- Personal Info ----
        canvas.drawText("Name: ${resume.personal.name}", 50f, y, titlePaint)
        y += 25f
        canvas.drawText("Email: ${resume.personal.email}", 50f, y, normalPaint)
        y += 20f
        canvas.drawText("Phone: ${resume.personal.phone}", 50f, y, normalPaint)
        y += 20f
        canvas.drawText("Address: ${resume.personal.address}", 50f, y, normalPaint)

        // ---- Finish page ----
        pdf.finishPage(page)

        // ---- Write to output safely ----
        context.contentResolver.openOutputStream(outputUri, "w")?.use { out ->
            pdf.writeTo(out)
            out.flush()
        } ?: throw IOException("Could not open output stream for $outputUri")
    } finally {
        pdf.close() // always close
    }
}


//private const val CREATE_FILE_REQUEST = 1001
//
//fun createPdfFile() {
//    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//        addCategory(Intent.CATEGORY_OPENABLE)
//        type = "application/pdf"
//        putExtra(Intent.EXTRA_TITLE, "resume.pdf") // Default filename
//    }
//    startActivityForResult(intent, CREATE_FILE_REQUEST)
//}
//
//fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    if (requestCode == CREATE_FILE_REQUEST && resultCode == RESULT_OK) {
//        data?.data?.let { uri ->
//            // ✅ This uri is guaranteed to be writable
//            generateResumePDFToURI(this, sampleResumeData(), null, uri)
//        }
//    }
//}
















































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