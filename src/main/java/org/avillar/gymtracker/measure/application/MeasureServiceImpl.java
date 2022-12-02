package org.avillar.gymtracker.measure.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.measure.domain.Measure;
import org.avillar.gymtracker.measure.domain.MeasureDao;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasureServiceImpl extends BaseService implements MeasureService {
    private static final String WORKOUT_NOT_FOUND = "The measure does not exist";

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
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        final List<Measure> measures = this.measureDao.findByUserAppOrderByDateDesc(userApp);
        final List<MeasureDto> measureDtos = new ArrayList<>(measures.size());

        for (final Measure measure : measures) {
            this.authService.checkAccess(measure);
            measureDtos.add(this.modelMapper.map(measure, MeasureDto.class));
        }

        return measureDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public MeasureDto getMeasure(final Long measureId) throws IllegalAccessException {
        final Measure measure = this.measureDao.findById(measureId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(measure);
        return this.modelMapper.map(measure, MeasureDto.class);
    }

    @Override
    @Transactional
    public MeasureDto createMeasure(final MeasureDto measureDto) {
        final Measure measure = this.modelMapper.map(measureDto, Measure.class);
        measure.setId(null);
        measure.setUserApp(this.authService.getLoggedUser());
        return this.modelMapper.map(this.measureDao.save(measure), MeasureDto.class);
    }

    @Override
    @Transactional
    public MeasureDto updateMeasure(final MeasureDto measureDto) throws IllegalAccessException {
        final Measure measureDb = this.measureDao.findById(measureDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(measureDb);
        final Measure measure = this.modelMapper.map(measureDto, Measure.class);
        measure.setUserApp(measureDb.getUserApp());
        return this.modelMapper.map(this.measureDao.save(measure), MeasureDto.class);
    }

    @Override
    @Transactional
    public void deleteMeasure(final Long measureId) throws IllegalAccessException {
        final Measure measure = this.measureDao.findById(measureId)
                .orElseThrow(() -> new EntityNotFoundException(WORKOUT_NOT_FOUND));
        this.authService.checkAccess(measure);
        this.measureDao.deleteById(measureId);
    }
}