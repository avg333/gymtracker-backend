package org.avillar.gymtracker.enums.infrastructure;

import org.avillar.gymtracker.enums.domain.ActivityLevelEnum;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.enums.domain.ProgramLevelEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EnumController {

    @GetMapping("/loadTypes")
    public ResponseEntity<List<LoadTypeEnum>> getLoadTypes() {
        final List<LoadTypeEnum> loadTypes = Arrays.asList(LoadTypeEnum.values());
        return ResponseEntity.ok(loadTypes);
    }

    @GetMapping("/programLevels")
    public ResponseEntity<List<ProgramLevelEnum>> getProgramLevels() {
        final List<ProgramLevelEnum> loadTypes = Arrays.asList(ProgramLevelEnum.values());
        return ResponseEntity.ok(loadTypes);
    }

    @GetMapping("/activityLevels")
    public ResponseEntity<List<ActivityLevelEnum>> getActivityLevels() {
        final List<ActivityLevelEnum> loadTypes = Arrays.asList(ActivityLevelEnum.values());
        return ResponseEntity.ok(loadTypes);
    }
}