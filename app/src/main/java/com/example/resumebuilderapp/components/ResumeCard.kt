package com.example.resumebuilderapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.resumebuilderapp.data.ResumeData

@Composable
fun ResumeCard(resume: ResumeData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = resume.personal.photoUrl.ifEmpty { null },
                    contentDescription = null,
                    modifier = Modifier.size(96.dp),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.weight(1f)) {
                    Text(
                        text = resume.personal.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = resume.personal.email, style = MaterialTheme.typography.bodyMedium)
                    Text(text = resume.personal.phone, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = resume.personal.address,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Qualifications",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${resume.qualificationsSkills.degree} . ${resume.qualificationsSkills.institute} . ${resume.qualificationsSkills.grade}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "From ${resume.qualificationsSkills.startDate} to ${resume.qualificationsSkills.endDate}",
                style = MaterialTheme.typography.bodyMedium
            )
            if(resume.qualificationsSkills.skills.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                Text(text = "Skills", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    text = resume.qualificationsSkills.skills,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(Modifier.height(12.dp))

            Text(text = "Experience", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(text = "${resume.experience.company} . ${resume.experience.startDate} . ${resume.experience.endDate}", style = MaterialTheme.typography.bodyMedium)
            Text(text = resume.experience.position, style = MaterialTheme.typography.bodyMedium)
            Text(text = resume.experience.description, style = MaterialTheme.typography.bodyMedium)

        }
    }
}