package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.RestBean;
import com.example.entity.dto.Student;
import com.example.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Resource
    StudentService service;

    /**
     * 查询所有数据
     *
     * @return
     */
    @GetMapping("/data")
    public RestBean<Page> data(@RequestParam int pageNum, @RequestParam int pageSize) {
        //分页构造器
        Page<Student> pageInfo = new Page<>(pageNum, pageSize);
        //条件构造器
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Student::getSno);
        service.page(pageInfo,wrapper);
        return RestBean.success(pageInfo);
    }

    /**
     * 根据条件查询
     *
     * @param sno
     * @return
     */
    @GetMapping("/condition")
    public RestBean<List<Student>> condition(@RequestParam String sno) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Student::getSno, sno);
        return RestBean.success(service.list(queryWrapper));
    }

    /**
     * 根据传过来的对象保存到数据库
     *
     * @param student
     * @return
     */
    @PostMapping("/add")
    public RestBean<String> addStudent(@RequestBody Student student) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getSno,student.getSno());
        if(service.getOne(wrapper)!=null){
            return RestBean.forbidden("学号重复");
        }
        service.save(student);
        return RestBean.success("数据保存成功");
    }

    /**
     * 根据条件删除对象
     *
     * @param student
     * @return
     */
    @PostMapping("/delete")
    public RestBean<String> deleteStudent(@RequestBody Student student) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Student::getSno, student.getSno());
        if (service.remove(queryWrapper)) {
            return RestBean.success("数据删除成功");
        }
        return RestBean.forbidden("数据异常");
    }

    /**
     * 更改数据
     * @param student
     * @return
     */
    @PostMapping("/update")
    public RestBean<String> updateStudent(@RequestBody Student student) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Student::getSno, student.getSno());
        if (service.update(student, queryWrapper)) {
            return RestBean.success("数据更新成功");
        }
        return RestBean.forbidden("数据异常");
    }
}
