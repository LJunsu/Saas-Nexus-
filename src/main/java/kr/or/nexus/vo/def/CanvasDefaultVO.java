package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CanvasDefaultVO implements Serializable {
    private Integer canvasId;

    private String projectId;

    private LocalDateTime canvasCrDate;

    private LocalDateTime canvasModDate;

    private String canvasDelyn;

    private static final long serialVersionUID = 1L;
}