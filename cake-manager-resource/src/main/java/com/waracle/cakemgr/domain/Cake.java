package com.waracle.cakemgr.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class Cake {
    private String title;
    private String desc;
    private String image;
}
