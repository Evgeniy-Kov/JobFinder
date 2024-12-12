package ru.practicum.android.diploma.data.dto

/**
 * @param address `Nullable` Хранит экземпляр класса `AddressDto` с информацией об адресе
 * @param area Обязательное поле, хранит экземпляр класса `AreaDto` (Регион)
 * @param employer Хранит экземпляр класса `EmployerDto` с информацией о работодателе
 * @param id Обязательное поле, хранит идентификатор вакансии
 * @param name Обязательное поле, хранит название вакансии
 * @param salary `Nullable` Хранит экземпляр класса `SalaryDto` с информацией о зарплате
 */

data class VacancyDto(
    val address: AddressDto?,
    val area: AreaRegionDto,
    val employer: EmployerDto,
    val id: String,
    val name: String,
    val salary: SalaryDto?,
//    val accept_incomplete_resumes: Boolean,
//    val accept_temporary: Boolean,
//    val adv_context: Any,
//    val adv_response_url: Any,
//    val alternate_url: String,
//    val apply_alternate_url: String,
//    val archived: Boolean,
//    val branding: Branding,
//    val contacts: Any,
//    val created_at: String,
//    val department: Any,
//    val employment: Employment,
//    val experience: Experience,
//    val has_test: Boolean,
//    val insider_interview: Any,
//    val is_adv_vacancy: Boolean,
//    val premium: Boolean,
//    val professional_roles: List<ProfessionalRole>,
//    val published_at: String,
//    val relations: List<Any>,
//    val response_letter_required: Boolean,
//    val response_url: Any,
//    val schedule: Schedule,
//    val show_logo_in_search: Boolean,
//    val snippet: Snippet,
//    val sort_point_distance: Any,
//    val type: Type,
//    val url: String,
//    val working_days: List<Any>,
//    val working_time_intervals: List<Any>,
//    val working_time_modes: List<Any>
)
