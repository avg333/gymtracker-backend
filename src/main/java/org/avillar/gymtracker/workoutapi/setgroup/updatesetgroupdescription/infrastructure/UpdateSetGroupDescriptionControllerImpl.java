package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetGroupDescriptionControllerImpl
    implements UpdateSetGroupDescriptionController {

  private final UpdateSetGroupDescriptionService updateSetGroupDescriptionService;

  @Override
  public UpdateSetGroupDescriptionResponse execute(
      final UUID setGroupId,
      final UpdateSetGroupDescriptionRequest updateSetGroupDescriptionRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return new UpdateSetGroupDescriptionResponse(
        updateSetGroupDescriptionService.execute(
            setGroupId, updateSetGroupDescriptionRequest.getDescription()));
  }
}
