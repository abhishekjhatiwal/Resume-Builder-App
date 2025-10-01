package com.example.resumebuilderapp.data

data class ResumeData(
    val personal: PersonalInfo,
    val qualificationsSkills: Qualifications,
    val experience: Experience
)

data class PersonalInfo(
    val name: String,
    val email: String,
    val phone: String,
    val address: String
)

data class Qualifications(
    val degree: String,
    val institute: String,
    val grade: String,
    val startDate: String,
    val endDate: String,
    val skills: String
)

data class Experience(
    val company: String,
    val position: String,
    val description: String,
    val startDate: String,
    val endDate: String
)

fun sampleResumeData() = ResumeData(
    personal = PersonalInfo(
        "John Doe",
        "john@example.com",
        "1234567890",
        "123 Main St, City"
    ),
    qualificationsSkills = Qualifications(
        "B.Sc. Computer Science",
        "ABC University",
        "A",
        "2015",
        "2018",
        "Kotlin, Java, Android, SQL"
    ),
    experience = Experience(
        "TechCorp",
        "Software Engineer",
        "Worked on Android applications",
        "2019",
        "2023"
    )
)
