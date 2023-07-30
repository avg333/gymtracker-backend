package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.UpdateSetGroupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper.UpdateSetGroupSetsControllereMapper;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponseInfrastructure;
import org.springframework.web.bind.annotation.RestController;

// TODO Finish this
@RestController
@RequiredArgsConstructor
public class UpdateSetGroupSetsControllerImpl implements UpdateSetGroupSetsController {

  private final UpdateSetGroupSetsService updateSetGroupSetsService;
  private final UpdateSetGroupSetsControllereMapper updateSetGroupSetsControllereMapper;

  @Override
  public UpdateSetGroupSetsResponseInfrastructure execute(
      final UUID setGroupId,
      final UpdateSetGroupSetsRequestInfrastructure updateSetGroupSetsRequestInfrastructure) {
    return updateSetGroupSetsControllereMapper.map(
        updateSetGroupSetsService.execute(
            setGroupId, updateSetGroupSetsRequestInfrastructure.getSetGroupId()));
  }
}
