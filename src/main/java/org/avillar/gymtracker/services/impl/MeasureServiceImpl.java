package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.MeasureRepository;
import org.avillar.gymtracker.model.dto.MeasureDto;
import org.avillar.gymtracker.model.entities.Measure;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.MeasureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MeasureServiceImpl implements MeasureService {
    private static final String NOT_FOUND_ERROR_MSG = "La medición no existe";

    private final MeasureRepository measureRepository;
    private final ModelMapper modelMapper;
    private final LoginService loginService;

    public MeasureServiceImpl(MeasureRepository measureRepository, ModelMapper modelMapper, LoginService loginService) {
        this.measureRepository = measureRepository;
        this.modelMapper = modelMapper;
        this.loginService = loginService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeasureDto> getAllLoggedUserMeasures() throws IllegalAccessException {
        final List<Measure> measures = this.measureRepository.findByUserAppOrderByDateDesc(this.loginService.getLoggedUser());
        return measures.stream().map(measure -> modelMapper.map(measure, MeasureDto.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MeasureDto getMeasure(final Long measureId) throws IllegalAccessException {
        final Measure measure = this.measureRepository.getById(measureId);
        this.loginService.checkAccess(measure);
        return this.modelMapper.map(measure, MeasureDto.class);
    }

    @Override
    @Transactional
    public MeasureDto createMeasure(final MeasureDto measureDto) {
        final Measure measure = this.modelMapper.map(measureDto, Measure.class);
        measure.setId(null);
        measure.setUserApp(this.loginService.getLoggedUser());
        return this.modelMapper.map(this.measureRepository.save(measure), MeasureDto.class);
    }

    @Override
    @Transactional
    public MeasureDto updateMeasure(final MeasureDto measureDto) throws IllegalAccessException {
        final Measure measureDb = this.measureRepository.findById(measureDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(measureDb);
        final Measure measure = this.modelMapper.map(measureDto, Measure.class);
        measure.setUserApp(measureDb.getUserApp());
        return this.modelMapper.map(this.measureRepository.save(measure), MeasureDto.class);
    }

    @Override
    @Transactional
    public void deleteMeasure(final Long measureId) throws IllegalAccessException {
        final Measure measure = this.measureRepository.findById(measureId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(measure);
        this.measureRepository.deleteById(measureId);
    }
}
