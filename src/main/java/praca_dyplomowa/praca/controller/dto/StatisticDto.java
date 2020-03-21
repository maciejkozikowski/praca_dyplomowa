package praca_dyplomowa.praca.controller.dto;

import lombok.Data;

@Data
public class StatisticDto {
    private Float average;
    private Float minimum;
    private Float maximum;
    private Float median;
    private Float sum;
    private Integer count;
}
