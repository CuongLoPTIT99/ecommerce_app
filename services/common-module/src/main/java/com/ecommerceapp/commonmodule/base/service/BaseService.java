package com.ecommerceapp.commonmodule.base.service;

import com.ecommerceapp.commonmodule.base.entity.BaseEntity;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
public abstract class BaseService<T extends BaseEntity, Tid> {
    public abstract BaseRepository<T, Tid> getRepository();
    public T add(T obj) throws RuntimeException {
        try {
            preAdd(obj);
            getRepository().save(obj);
            postAdd(obj);
        } catch (Exception e) {
            log.error("Error while adding object to DB", e);
            throw new RuntimeException("Error while adding object to DB", e);
        }
        return obj;
    }

    public T edit(T obj) throws RuntimeException {
        try {
            T current = getById((Tid) obj.getId());
            if (current == null) throw new RuntimeException("Data is not exist in DB");
            preEdit(current);
            BeanUtils.copyProperties(obj, current, "id", "createdAt", "createdBy");
            getRepository().save(current);
            postEdit(current);
        } catch (Exception e) {
            log.error("Error while editing object to DB", e);
            throw new RuntimeException("Error while editing object to DB", e);
        }
        return obj;
    }

    public void deleteById(Tid id) throws RuntimeException {
        try {
            preDeleteById(id);
            getRepository().deleteById(id);
            postDeleteById(id);
        } catch (Exception e) {
            log.error("Error while deleting object to DB", e);
            throw new RuntimeException("Error while deleting object to DB", e);
        }
    }

    public T getById(Tid id) throws RuntimeException {
        T obj = null;
        try {
            preGetById(id);
            obj = getRepository().findById(id).orElse(null);
            postGetById(obj);
        } catch (Exception e) {
            log.error("Error while adding object to DB", e);
            throw new RuntimeException("Error while adding object to DB", e);
        }
        return obj;
    }

    public List<T> getAll() throws RuntimeException {
        List<T> result;
        try {
            result = StreamSupport.stream(getRepository().findAll().spliterator(), false)
                    .toList();
        } catch (Exception e) {
            log.error("Error while getting all object from DB", e);
            throw new RuntimeException("Error while getting al object from DB", e);
        }
        return result;
    }

    public T preAdd(T obj) throws RuntimeException {
        return obj;
    }

    public T postAdd(T obj) throws RuntimeException {
        return obj;
    }

    public T preEdit(T obj) throws RuntimeException {
        return obj;
    }

    public T postEdit(T obj) throws RuntimeException {
        return obj;
    }

    public void preDeleteById(Tid id) throws RuntimeException {}

    public void postDeleteById(Tid id) throws RuntimeException {}

    public void preGetById(Tid id) throws RuntimeException {}

    public void postGetById(T obj) throws RuntimeException {}
}
