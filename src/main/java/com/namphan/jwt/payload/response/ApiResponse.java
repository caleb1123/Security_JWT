package com.namphan.jwt.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // bỏ field null khi trả về
public class ApiResponse<T> {
    private int code;        // mã code (vd: 200, 400, 500...)
    private String message;  // thông điệp
    private T data;          // dữ liệu trả về (nếu success)


}