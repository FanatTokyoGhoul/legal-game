package ru.vsu.legal.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GameContinueRequest {
    private String name;
    private Integer term;
    private List<TreatyRequest> newTreaty;
//    private List<TreatyUpdateRequest> treatyUpdateRequests;
    private List<Long> idsTreatyToDelete;
}
