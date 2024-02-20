package org.nautilusapp.nautilus.validation

fun createMeetingValidArgs(args: CreateMeetingArgs): ValidArgsResult {
    val reasons = mutableListOf<String>()
    if(args.description.isBlank()) reasons.add( "Description cannot be blank")
    if(args.type.isBlank()) reasons.add("Type cannot be blank")
    if(args.endTimeMs < args.startTimeMs) reasons.add("End time cannot be before start time")
    return ValidArgsResult(reasons.isEmpty(), reasons)
}

data class ValidArgsResult(val ok: Boolean, val reasons: List<String>)

data class CreateMeetingArgs(val type: String, val description: String, val startTimeMs: Long, val endTimeMs: Long, val meetingValue: Int, val attendancePeriod: String)
