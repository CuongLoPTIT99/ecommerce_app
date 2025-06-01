package com.ecommerceapp.commonmodule.base.service;

import com.ecommerceapp.commonmodule.base.entity.BaseEntity;
import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
public abstract class BaseService<T extends BaseEntity, R extends Object, S extends Object, Tid> {
    public abstract BaseRepository<T, Tid> getRepository();

    public abstract BaseMapper<T, R, S> getMapper();

    public S create(R input) throws RuntimeException {
        T obj = null;
        S output;
        try {
            obj = getMapper().fromDTO(input);
            obj = preCreate(obj, input);
            getRepository().save(obj);
            output = getMapper().toDTO(obj);
            output = postCreate(obj, input, output);
        } catch (Exception e) {
            log.error("Error while adding object to DB", e);
            throw new RuntimeException("Error while adding object to DB", e);
        }
        return output;
    }

    public S update(R input) throws RuntimeException {
        T obj = null, current;
        S output;
        try {
            obj = getMapper().fromDTO(input);
            current = getRepository().findById((Tid) obj.getId()).orElse(null);
            if (current == null) throw new RuntimeException("Data is not exist in DB");
            obj = preUpdate(obj, current, input);
            BeanUtils.copyProperties(obj, current, "id", "createdAt", "createdBy");
            getRepository().save(current);
            output = getMapper().toDTO(current);
            output = postUpdate(current, input, output);
        } catch (Exception e) {
            log.error("Error while editing object to DB", e);
            throw new RuntimeException("Error while editing object to DB", e);
        }
        return output;
    }

    public void deleteById(Tid id) throws RuntimeException {
        try {
            T current = getRepository().findById(id).orElse(null);
            preDeleteById(current, id);
            getRepository().deleteById(id);
            postDeleteById(current, id);
        } catch (Exception e) {
            log.error("Error while deleting object to DB", e);
            throw new RuntimeException("Error while deleting object to DB", e);
        }
    }

    public S getById(Tid id) throws RuntimeException {
        T obj = null;
        try {
            preGetById(id);
            obj = getRepository().findById(id).orElse(null);
            postGetById(obj);
        } catch (Exception e) {
            log.error("Error while getting object to DB", e);
            throw new RuntimeException("Error while adding object to DB", e);
        }
        return getMapper().toDTO(obj);
    }

    public List<S> getAll() throws RuntimeException {
        List<T> result;
        try {
            result = StreamSupport.stream(getRepository().findAll().spliterator(), false)
                    .toList();
        } catch (Exception e) {
            log.error("Error while getting all object from DB", e);
            throw new RuntimeException("Error while getting al object from DB", e);
        }
        return result.stream()
                .map(getMapper()::toDTO)
                .toList();
    }

    public T preCreate(T obj, R input) throws RuntimeException {
        return obj;
    }

    public S postCreate(T obj, R input, S output) throws RuntimeException {
        return output;
    }

    public T preUpdate(T obj, T current, R input) throws RuntimeException {
        return obj;
    }

    public S postUpdate(T obj, R input, S output) throws RuntimeException {
        return output;
    }

    public void preDeleteById(T obj, Tid id) throws RuntimeException {}

    public void postDeleteById(T obj, Tid id) throws RuntimeException {}

    public void preGetById(Tid id) throws RuntimeException {}

    public void postGetById(T obj) throws RuntimeException {}
}
