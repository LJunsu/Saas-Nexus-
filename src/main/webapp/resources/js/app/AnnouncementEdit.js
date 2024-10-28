const textAreaId = "booardContent";
const contextPath = document.body.dataset.contextPath;

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("[data-atch-file-id][data-file-sn]").forEach(el => {
        el.addEventListener("click", e => {
            e.preventDefault();

            let attachedFileId = el.dataset.atchFileId;
            let attachedFileDetailId = el.dataset.fileSn;

            console.log("attachedFileId:", attachedFileId);
            console.log("attachedFileDetailId:", attachedFileDetailId);

            fetch(`${contextPath}/attached/announcement/${attachedFileId}/${attachedFileDetailId}`, {
                method: "DELETE",
                headers: {
                    "Accept": "application/json"
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(json => {
                if (json.success) {
                    el.parentElement.remove();
                } else {
                    console.error("Failed to delete file:", json.message);
                }
            })
            .catch(error => {
                console.error("Error during fetch:", error);
            });
        });
    });
});
