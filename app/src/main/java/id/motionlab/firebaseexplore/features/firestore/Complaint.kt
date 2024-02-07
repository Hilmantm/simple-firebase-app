package id.motionlab.firebaseexplore.features.firestore

data class Complaint(

    val name: String,
    val major: String,
    val complaint: String

) {

    fun getComplaintHashMap(): HashMap<String, String> {
        return hashMapOf(
            "name" to name,
            "major" to major,
            "complaint" to complaint
        )
    }

}
