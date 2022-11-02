package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.MeasureDao;
import org.avillar.gymtracker.model.dao.UserDao;
import org.avillar.gymtracker.model.dto.MeasureDto;
import org.avillar.gymtracker.model.entities.Measure;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.MeasureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeasureServiceImpl extends BaseService implements MeasureService {
    private static final String NOT_FOUND_ERROR_MSG = "La medición no existe";

    private final MeasureDao measureDao;
    private final UserDao userDao;

    @Autowired
    public MeasureServiceImpl(MeasureDao measureDao, UserDao userDao) {
        this.measureDao = measureDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasureDto> getAllUserMeasures(final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<Measure> measures = this.measureDao.findByUserAppOrderByDateDesc(userApp);
        final List<MeasureDto> measureDtos = new ArrayList<>(measures.size());

        for(final Measure measure: measures){
            this.loginService.checkAccess(measure);
            measureDtos.add(this.modelMapper.map(measure, MeasureDto.class));
        }

        return measureDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public MeasureDto getMeasure(final Long measureId) throws IllegalAccessException {
        final Measure measure = this.measureDao.getById(measureId);
        this.loginService.checkAccess(measure);
        return this.modelMapper.map(measure, MeasureDto.class);
    }

    @Override
    @Transactional
    public MeasureDto createMeasure(final MeasureDto measureDto) {
        final Measure measure = this.modelMapper.map(measureDto, Measure.class);
        measure.setId(null);
        measure.setUserApp(this.loginService.getLoggedUser());
        return this.modelMapper.map(this.measureDao.save(measure), MeasureDto.class);
    }

    @Override
    @Transactional
    public MeasureDto updateMeasure(final MeasureDto measureDto) throws IllegalAccessException {
        final Measure measureDb = this.measureDao.findById(measureDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(measureDb);
        final Measure measure = this.modelMapper.map(measureDto, Measure.class);
        measure.setUserApp(measureDb.getUserApp());
        return this.modelMapper.map(this.measureDao.save(measure), MeasureDto.class);
    }

    @Override
    @Transactional
    public void deleteMeasure(final Long measureId) throws IllegalAccessException {
        final Measure measure = this.measureDao.findById(measureId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(measure);
        this.measureDao.deleteById(measureId);
    }
}