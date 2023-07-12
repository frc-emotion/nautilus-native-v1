package org.team2658.emotion.userauth

data class User(
    val username: String,
    val JWT: String, //authentication token for request headers
    val firstName: String,
    val lastName: String,
    val email: String,
    val accessLevel: AccessLevel = AccessLevel.NONE,
    val subteam: Subteam = Subteam.NONE,
    val permissions: UserPermissions = UserPermissions() //by default no permissions
)


//sample JSON:
/*  {
        "username": "exampleUser",
        "JWT":"2a10zJ.xPmuitlspZXGPSzM6AuTcCvQLkpxyGtCtzf.Ug0zIjG4GtX5vC",
        "firstName": "Example",
        "lastName": "User",
        "email": "example@example.com",
        "accessLevel": "BASE",
        "subteam": "SOFTWARE",
        "permissions": {
            "submitScoutingData": false,
            "viewScoutingData": false,
            "inPitScouting": false
        }
*/