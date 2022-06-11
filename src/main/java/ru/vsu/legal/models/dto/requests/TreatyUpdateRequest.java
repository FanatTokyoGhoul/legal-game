package ru.vsu.legal.models.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TreatyUpdateRequest {
    private List<Long> ids;
    private List<TreatyRequest> treatyRequests;
}
