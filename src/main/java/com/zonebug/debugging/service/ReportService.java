package com.zonebug.debugging.service;

import com.zonebug.debugging.domain.bug.Bug;
import com.zonebug.debugging.domain.checklist.CheckListRepository;
import com.zonebug.debugging.domain.drug.Drug;
import com.zonebug.debugging.domain.drug.DrugRepository;
import com.zonebug.debugging.domain.scenario.Scenario;
import com.zonebug.debugging.domain.scenario.ScenarioRepository;
import com.zonebug.debugging.domain.scenario.ScenarioSpecification;
import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.domain.user.UserRepository;
import com.zonebug.debugging.dto.CheckListDTO;
import com.zonebug.debugging.dto.DrugListDTO;
import com.zonebug.debugging.dto.ReportImageDTO;
import com.zonebug.debugging.dto.ReportItemDTO;
import com.zonebug.debugging.dto.response.ReportResponseDTO;
import com.zonebug.debugging.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;

    private final ScenarioRepository scenarioRepository;
    private final CheckListRepository checkListRepository;
    private final DrugRepository drugRepository;


    public ReportResponseDTO getReport(CustomUserDetails authUser, Integer period) {
        User user = authUser.getUser();

        Specification<Scenario> spec = (root, query, criteriaBuilder) -> null;
        spec = spec.and(ScenarioSpecification.findScenario(period, user));
        List<Scenario> scenarioList = scenarioRepository.findAll(spec);

        List<ReportItemDTO> reportItemDTOList = new ArrayList<>();

        for(Scenario s : scenarioList){
            String image = s.getImage();
            Bug bug = s.getBug();
            String bugName = bug.getSpecies();
            Date date = s.getCreatedAt();
            ReportImageDTO data = new ReportImageDTO(image, bugName, date);

            List<String> checkListContents = checkListRepository.findContentsByBug(bug);
            CheckListDTO checkListData = new CheckListDTO(bugName, checkListContents);

            List<Drug> drugListContents = drugRepository.findByBug(bug);
            DrugListDTO drugListData = new DrugListDTO(bugName, drugListContents);

            ReportItemDTO reportItem = new ReportItemDTO(data, checkListData, drugListData);
            reportItemDTOList.add(reportItem);
        }


        return new ReportResponseDTO(reportItemDTOList);
    }
}
