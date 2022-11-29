package org.avillar.gymtracker.measure.application;

import java.util.List;

public interface MeasureService {

    List<MeasureDto> getAllUserMeasures(Long userId) throws IllegalAccessException;

    MeasureDto getMeasure(Long measureId) throws IllegalAccessException;

    MeasureDto createMeasure(MeasureDto measureDto);

    MeasureDto updateMeasure(MeasureDto measureDto) throws IllegalAccessException;

    void deleteMeasure(Long measureId) throws IllegalAccessException;
}
