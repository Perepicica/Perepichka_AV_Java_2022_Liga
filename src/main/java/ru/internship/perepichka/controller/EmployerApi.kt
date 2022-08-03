package ru.internship.perepichka.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.internship.perepichka.dto.GetEmployeeDTO
import ru.internship.perepichka.dto.PostPutEmployeeDTO
import ru.internship.perepichka.exception.BadIdException
import ru.internship.perepichka.exception.HasReferenceException
import ru.internship.perepichka.service.implementation.EmployeeServiceImpl
import javax.validation.Valid
import kotlin.jvm.optionals.getOrNull

const val BASE_KOTLIN_API = "/kapi"
const val EMPLOYEES_ENDPOINT = "/employees"

@RestController
@RequestMapping(BASE_KOTLIN_API + EMPLOYEES_ENDPOINT)
class EmployeesApi(
    private val employeeServiceImpl: EmployeeServiceImpl
) {

    @GetMapping
    fun getAllEmployees(): ResponseEntity<List<GetEmployeeDTO>> {
        val employees = employeeServiceImpl
            .allEmployees
            .map { it.asGetEmployeeDTO }

        return if (employees.isEmpty()) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else ResponseEntity(employees, HttpStatus.OK)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable(name = "id") id: String): ResponseEntity<GetEmployeeDTO> {
        val employee = employeeServiceImpl
            .getEmployeeById(id)
            .getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(employee.asGetEmployeeDTO, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateEmployee(
        @PathVariable(name = "id") id: String,
        @RequestBody request: @Valid PostPutEmployeeDTO
    ): ResponseEntity<PostPutEmployeeDTO> {
        return try {
            employeeServiceImpl.updateEmployee(id, request.asEmployee)
            ResponseEntity.ok().body(request)
        } catch (e: BadIdException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEmployee(@PathVariable(name = "id") id: String): ResponseEntity<String> {
        return try {
            employeeServiceImpl.deleteEmployee(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: HasReferenceException) {
            ResponseEntity(e.message, HttpStatus.UNPROCESSABLE_ENTITY)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}