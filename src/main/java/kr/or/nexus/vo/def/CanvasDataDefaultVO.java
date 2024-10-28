package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CanvasDataDefaultVO implements Serializable {
    private String canvasDataId;

    private String canvasId;

    private LocalDateTime canvasDataCrDate;

    private String canvasEmData;

    private String canvasDataType;

    private static final long serialVersionUID = 1L;
}