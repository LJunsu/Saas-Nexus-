package kr.or.nexus.canvas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("group/{groupId}/project/{projectId}/canvas")
public class CanvasController {

	@GetMapping
	public String canvasView(
		@PathVariable String groupId
		, @PathVariable String projectId
	) {
		return "tiles2:canvas/canvas";
	}
}