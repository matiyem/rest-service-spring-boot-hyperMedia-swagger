package com.example.demo.restController;
/*
*Create by User on 7/27/2021
*/

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeModelAssembler;
import com.example.demo.entity.EmployeeRepository;
import com.example.demo.exception.EmployeeNotFoundException;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequestMapping("/restService")
@Api(tags = "Tara web service")
public class EmployeeController {

    private final EmployeeRepository repository;
    @Autowired
    private EmployeeModelAssembler assembler;

    //در اصل داریم این کلاس را بوسیله  contructor اینجکت میکنیم داخل این کلاس
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

//    @RequestMapping(value = "/employees",method = RequestMethod.GET)
//    List<Employee> all() {
//        return repository.findAll();
//    }

//    @PostMapping("/employees")
//    Employee newEmployee(@RequestBody Employee newEmployee) {
//        return repository.save(newEmployee);
//    }

//    @GetMapping("/employee/{id}")
//    Employee one(@PathVariable Long id) {
//        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
//    }

    @PutMapping(value = "/employee/{id}")

    Employee repalceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> one(@PathVariable Long id) {
        //برای این کار باید hateoas به فایل pom اضافه کنیم
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));

    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(employee -> EntityModel.of(employee, linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees1")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
        //responseEntity برای این است که ریسپانس کد 201 در صورت موفقیت آمیز بسازه

        EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));
        return ResponseEntity.created((entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())).body(entityModel);
    }

}
