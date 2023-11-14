package net.ddns.sinapouya.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private Integer resultCode = 1;
    private String resultMessage = "operation has done successfully.";
    private T info;

    public Response(T info) {
        this.info = info;
    }

}
