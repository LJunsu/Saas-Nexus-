document.addEventListener('DOMContentLoaded', function() {
	connectWs2();
	loadChatRooms();
	setupAddRoomModal(); // 모달 기능 설정
	setupChatRoomMenu(); // 채팅방 메뉴 기능 설정
	setupInviteModal();
	setupMembersDropdown();

	const roomId = document.getElementById('room-id').value;

	updateMemberCount(roomId);

});

function connectWs2() {
	if (window.socket && window.socket.readyState === WebSocket.OPEN) {
		console.log('WebSocket is already connected.');
		return;
	}

	const contextPath = document.body.dataset.contextPath;
	const domainName = location.hostname;
	const socketUrl = `http://${domainName}${contextPath}/chatting`;

	window.socket = new SockJS(socketUrl);

	window.socket.onopen = function() {
		console.log('WebSocket connected.');
	};

	window.socket.onmessage = handleMessageFromServer;

	window.socket.onclose = function() {
		console.log('WebSocket disconnected. Attempting to reconnect...');
		setTimeout(connectWs2, 3000); // 3초 후에 재연결 시도
	};

	window.socket.onerror = function(error) {
		console.error('WebSocket error:', error);
	};

	const sendMessageBtn = document.getElementById('send-message-btn');
	if (sendMessageBtn) {
		sendMessageBtn.addEventListener('click', function(e) {
			e.preventDefault();
			sendChatMessage();
		});
	}

	const messageInput = document.getElementById('message-input');
	if (messageInput) {
		messageInput.addEventListener('keydown', function(e) {
			if (e.key === 'Enter' && !e.shiftKey) {
				e.preventDefault(); // Enter 키의 기본 동작을 방지
				sendChatMessage();
			}
		});
	}
}


function setupChatRoomMenu() {
	const menuToggle = document.getElementById('menu-toggle');
	const dropdownMenu = document.getElementById('dropdown-menu');
	const leaveRoomBtn = document.getElementById('leave-room-btn');
	const inviteRoomBtn = document.getElementById('invite-room-btn');
	const userId = document.getElementById('user-id').value;
	const groupId = document.getElementById('group-id').value;

	if (menuToggle && dropdownMenu) {
		menuToggle.addEventListener('click', function() {
			dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
		});
	}

	// 페이지의 다른 부분을 클릭하면 드롭다운 메뉴를 숨김
	window.addEventListener('click', function(event) {
		if (!menuToggle.contains(event.target) && !dropdownMenu.contains(event.target)) {
			dropdownMenu.style.display = 'none';
		}
	});

	// 채팅방 나가기 버튼 클릭 처리
	leaveRoomBtn.addEventListener('click', function() {
		const roomId = document.getElementById('room-id').value;
		const userId = document.getElementById('user-id').value;
		Swal.fire({
			title: '정말로 이 채팅방에서 나가시겠습니까?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonText: '확인',
			cancelButtonText: '취소'
		}).then((result) => {
			if (result.isConfirmed) {
				// 사용자가 확인 버튼을 클릭한 경우
				leaveChatRoom(roomId, userId);
			}
		});

	});

	// 채팅방 초대 버튼 클릭 처리
	inviteRoomBtn.addEventListener('click', function() {
		if (menuToggle && dropdownMenu) {
			menuToggle.addEventListener('click', function() {
				dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
				if (dropdownMenu.style.display === 'block') {
					updateRoomMembers(); // 드롭다운 메뉴가 열릴 때 회원 목록 업데이트
				}
			});
		}

		// 초대할 그룹원 목록 로드
		loadGroupMembersForInvite();
	});
}
function leaveChatRoom(roomId, userId) {
	const contextPath = document.body.dataset.contextPath;
	const groupId = document.getElementById('group-id').value;

	$.ajax({
		url: `${contextPath}/group/${groupId}/chat/leave/${roomId}`, // 채팅방 ID
		type: 'POST',
		success: function(response) {
			Swal.fire({
				icon: 'success',
				title: '채팅방 나가기가 완료됬습니다.',
				confirmButtonText: '확인',
			}).then((result) => {
				if (result.isConfirmed) {
					// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션
					document.querySelectorAll('.chat-window').forEach(win => win.classList.remove('active'));
					const roomElement = document.querySelector(`#chat-room-item[data-room-id='${roomId}']`);
					if (roomElement) {
						roomElement.classList.remove('active');
					}

					// 페이지 새로고침
					location.reload();
				}
			});

			// 현재 채팅방 닫기
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.error('채팅방 나가기 중 오류 발생:', textStatus, errorThrown);
			Swal.fire({
				icon: 'warning',
				title: '채팅방 나가기중 오류가 발생했습니다.',
				confirmButtonText: '확인',
			})
		}
	});
}


// 채팅 메시지를 서버로 전송하고 채팅방 목록을 업데이트
function sendChatMessage() {
	let message = document.getElementById('message-input').value;
	let roomId = document.getElementById('room-id').value;
	let userId = document.getElementById('user-id').value;
	let userName = document.getElementById('user-name').value;
	let memberProfilePict = document.getElementById('member-profict').value;
	if (message.trim() !== "") {
		let timestamp = new Date().toISOString();
		let messageId = `${userId}_${timestamp}`; // 메시지 ID 생성

		console.log(userName);


		sendMessageToServer(roomId, userName, message, timestamp, messageId, memberProfilePict);
		document.getElementById('message-input').value = "";
		addMessageToChat(roomId, userName, message, new Date(timestamp), messageId, memberProfilePict);
		saveMessageToLocalStorage(roomId, userName, message, timestamp, messageId, memberProfilePict);

		// 채팅방 목록 비동기 갱신
		loadChatRooms();
	}
}
function setupInviteModal() {
	const inviteRoomBtn = document.getElementById('invite-room-btn');
	const closeInviteModalBtn = document.getElementById('close-invite-modal-btn');
	const sendInviteBtn = document.getElementById('invite-members-btn');

	// 새 이벤트 리스너 등록
	inviteRoomBtn.addEventListener('click', openInviteModal);
	closeInviteModalBtn.addEventListener('click', closeInviteModal);
	sendInviteBtn.addEventListener('click', sendInvite);
}


function openInviteModal() {
	const inviteModal = document.getElementById('invite-room-modal');
	if (inviteModal.style.display === 'block') {
		return; // 이미 열려있으면 아무 작업도 하지 않음
	}
	inviteModal.style.display = 'block';
	loadGroupMembersForInvite(); // 그룹원 목록을 로드
}

function closeInviteModal() {
	const inviteModal = document.getElementById('invite-room-modal');
	inviteModal.style.display = 'none';
	location.reload(); // 페이지 새로고침
}
function sendInvite() {
	const roomId = document.getElementById('room-id').value;
	const selectedMembers = Array.from(document.querySelectorAll('#invite-members input:checked')).map(input => input.value);

	if (selectedMembers.length === 0) {
		Swal.fire({
			icon: 'warning',
			title: '초대할멤버를 선택하세요!',
			confirmButtonText: '확인',
		}).then((result) => {
			if (result.isConfirmed) {
				// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션

				return;
			}
		});
	}

	// 초대 요청을 보낸 후 이동할 채팅방 ID를 저장합니다.
	const moveToRoomId = roomId;
	sendInvitesToServer(roomId, selectedMembers, moveToRoomId);
}


let isLoadingMembers = false;

function loadGroupMembersForInvite() {
	if (isLoadingMembers) return; // 이미 로딩 중이면 리턴
	isLoadingMembers = true;

	const contextPath = document.body.dataset.contextPath;
	const groupId = document.getElementById('group-id').value;
	const url = `${contextPath}/group/${groupId}/chat/groupMembers`;
	const chattingRoomId = document.getElementById('room-id').value;
	const inviteMembersContainer = document.getElementById('invite-members');

	inviteMembersContainer.innerHTML = ''; // 기존 내용을 비움

	$.ajax({
		url: url,
		method: 'GET',
		success: function(allMembers) {
			console.log('All Members:', allMembers);

			const userId = document.getElementById('user-id').value;
			console.log('Current User ID:', userId);

			$.ajax({
				url: `${contextPath}/group/${groupId}/chat/members/${chattingRoomId}`,
				method: 'GET',
				success: function(roomMembers) {
					console.log('Room Members:', roomMembers);
					const roomMemberIds = new Set(roomMembers.map(member => member.memberId));


					allMembers.forEach(function(member) {
						if (member.memberId !== userId && !roomMemberIds.has(member.memberId)) {
							const memberElement = document.createElement('div');
							memberElement.innerHTML = `
                                <input type="checkbox" id="invite-member-${member.memberId}" name="inviteMembers" value="${member.memberId}">
                                <label for="invite-member-${member.memberId}">${member.memberName}</label>
                            `;
							inviteMembersContainer.appendChild(memberElement);
						}
					});
					isLoadingMembers = false;
				},
				error: function(jqXHR, textStatus, errorThrown) {
					console.error('채팅방 멤버 목록을 가져오는 중 오류 발생:', textStatus, errorThrown);
					alert('채팅방 멤버 목록을 가져오는 중 오류가 발생했습니다.');
					isLoadingMembers = false;
				}
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.error('그룹원 목록을 가져오는 중 오류 발생:', textStatus, errorThrown);
			alert('그룹원 목록을 가져오는 중 오류가 발생했습니다.');
			isLoadingMembers = false;
		}
	});
}



function sendInvitesToServer(roomId, members, moveToRoomId) {
	const contextPath = document.body.dataset.contextPath;
	const groupId = document.getElementById('group-id').value;
	const url = `${contextPath}/group/${groupId}/chat/invite`;

	$.ajax({
		url: url,
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({
			roomId: roomId,
			members: members
		}),
		success: function() {
			Swal.fire({
				icon: 'success',
				title: '초대가 완료되었습니다!',
				confirmButtonText: '확인'
			}).then((result) => {
				if (result.isConfirmed) {
					// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션

					document.getElementById('invite-room-modal').style.display = 'none';
					openChatRoom(moveToRoomId);
					location.reload(); // 페이지 새로고침
				}
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.error('초대 중 오류 발생:', textStatus, errorThrown);
			alert('초대 중 오류가 발생했습니다.');
		}
	});
}

function sendMessageToServer(roomId, sender, content, timestamp, messageId, memberProfilePict) {
	const chatMessage = {
		roomId: roomId,
		sender: sender,
		content: content,
		timestamp: timestamp,
		messageId: messageId,
		memberProfilePict: memberProfilePict

	};

	console.log(memberProfilePict);

	window.socket.send(JSON.stringify(chatMessage));
}

function handleMessageFromServer(event) {
	try {
		let message = JSON.parse(event.data);
		let currentRoomId = document.getElementById('room-id')?.value;

		if (message.roomId === currentRoomId) {
			addMessageToChat(message.roomId, message.sender, message.content, new Date(message.timestamp), message.messageId, message.memberProfilePict);
			saveMessageToLocalStorage(message.roomId, message.sender, message.content, message.timestamp, message.messageId, message.memberProfilePict);
		}

		// 수신된 메시지에 따라 채팅방 목록의 최근 메시지 갱신
		updateRecentMessage(message.roomId, `${message.sender}: ${message.content}`, message.timestamp);

	} catch (e) {
		console.error('Error parsing message:', e);
	}
}
function updateRecentMessage(roomId, message, timestamp) {
	const recentMessageElement = document.getElementById(`recent-message-${roomId}`);
	const timestampElement = document.getElementById(`message-time-${roomId}`);

	console.log(`Updating room ${roomId} with message: ${message} at ${timestamp}`);

	if (!recentMessageElement || !timestampElement) {
		console.warn(`Elements for room ${roomId} not found.`);
		return;
	}

	recentMessageElement.textContent = message;
	timestampElement.textContent = new Date(timestamp).toLocaleTimeString();

	console.log('Update successful');
}


function addMessageToChat(roomId, sender, text, timestamp, messageId, memberProfilePict) {

	if (!window.messageIds) {
		window.messageIds = new Set();
	}

	// 중복 메시지 체크
	if (window.messageIds.has(messageId)) {
		console.log('Duplicate message detected, skipping.');
		return;
	}
	console.log(memberProfilePict);


	window.messageIds.add(messageId);

	const chatMessages = document.getElementById('chat-messages');
	if (chatMessages) {
		let messageElement = '';

		// 사용자의 메시지와 상대방의 메시지를 구분하여 다른 HTML 구조 사용
		const userName = document.getElementById('user-name').value;
		if (sender === userName) {
			messageElement = `
            <li class="chat-message chat-message-right">
                <div class="d-flex overflow-hidden">
                    <div class="chat-message-wrapper flex-grow-1">
                        <span id="sender">${sender || 'Unknown'}</span>
                        <div id="message-text" class="chat-message-text">
                            <p class="mb-0">${text || 'No content'}</p>
                        </div>
                        <div class="text-end text-muted mt-1">
                            <small>${timestamp ? `${new Date(timestamp).toLocaleDateString()} ${new Date(timestamp).toLocaleTimeString()}` : 'Unknown'}</small>
                        </div>
                    </div>
				<div class="user-avatar flex-shrink-0 ms-3">
                                <div class="avatar avatar-sm">
                               <img src="${memberProfilePict}" alt="Avatar" class="rounded-circle" />
                            </div>
                       </div>
                </div>
            </li>`;
		} else {
			messageElement = `
            <li class="chat-message">
                <div class="d-flex overflow-hidden">
<div class="user-avatar flex-shrink-0 ms-3">
                                <div class="avatar avatar-sm">
                               <img src="${memberProfilePict}" alt="Avatar" class="rounded-circle" />
                            </div>
                       </div>
                    <div class="chat-message-wrapper flex-grow-1">
                        <span id="sender">${sender || 'Unknown'}</span>
                        <div id="message-text" class="chat-message-text">
                            <p class="mb-0">${text || 'No content'}</p>
                        </div>
                        <div id="message-time" class="text-muted mt-1">
                            <small>${timestamp ? `${new Date(timestamp).toLocaleDateString()} ${new Date(timestamp).toLocaleTimeString()}` : 'Unknown'}</small>
                        		</div>
                    			</div>

                </div>
            </li>`;
		}

		// 새로운 메시지를 추가
		chatMessages.innerHTML += messageElement;
		chatMessages.scrollTop = chatMessages.scrollHeight; // 새로운 메시지가 추가되면 스크롤을 맨 아래로
	}
}


function saveMessageToLocalStorage(roomId, sender, content, timestamp, messageId, memberProfilePict) {
	let messages = JSON.parse(localStorage.getItem(`chatMessages_${roomId}`)) || [];

	// 중복 메시지 확인 및 저장
	if (!messages.some(message => message.messageId === messageId)) {
		messages.push({
			sender: sender,
			roomId: roomId,
			content: content,
			timestamp: timestamp,
			messageId: messageId,
			memberProfilePict: memberProfilePict
		});
		localStorage.setItem(`chatMessages_${roomId}`, JSON.stringify(messages));
	}
}

function loadMessagesFromLocalStorage(roomId) {
	const messages = JSON.parse(localStorage.getItem(`chatMessages_${roomId}`)) || [];
	messages.sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp));
	return messages;
}

function loadChatRooms() {
    const chatRooms = Array.from(document.querySelectorAll('#chat-room-item'));

    // 채팅방 리스트를 최근 메시지 순으로 정렬
    chatRooms.sort((a, b) => {
        const roomIdA = a.getAttribute('data-room-id');
        const roomIdB = b.getAttribute('data-room-id');

        const lastMessageA = getLastMessageFromLocalStorage(roomIdA);
        const lastMessageB = getLastMessageFromLocalStorage(roomIdB);

        if (lastMessageA && lastMessageB) {
            return new Date(lastMessageB.timestamp) - new Date(lastMessageA.timestamp);
        } else if (lastMessageA) {
            return -1;
        } else if (lastMessageB) {
            return 1;
        } else {
            return 0;
        }
    });

    // 채팅방 리스트를 정렬된 순서로 다시 렌더링
    const chatRoomList = document.getElementById('chat-room-list');
    if (chatRoomList) {
        chatRooms.forEach(room => {
            chatRoomList.appendChild(room); // 이 작업만으로 요소의 순서가 변경됨
        });
    }

    // 각 채팅방의 최근 메시지와 타임스탬프 업데이트
    chatRooms.forEach(room => {
        const roomId = room.getAttribute('data-room-id');
        const lastMessage = getLastMessageFromLocalStorage(roomId);

        if (lastMessage) {
            const recentMessageElement = room.querySelector(`#recent-message-${roomId}`);
            const timestampElement = room.querySelector(`#message-time-${roomId}`);

            if (recentMessageElement && timestampElement) {
                recentMessageElement.textContent = `${lastMessage.sender}: ${lastMessage.content}`;
                timestampElement.textContent = new Date(lastMessage.timestamp).toLocaleTimeString();
            } else {
                console.log(`Elements not found for roomId: ${roomId}`);
            }
        } else {
            console.log(`No last message found for roomId: ${roomId}`);
        }
    });

    // 가장 최근 메시지가 있는 채팅방 열기
    const mostRecentRoomId = getMostRecentRoomId();
    if (mostRecentRoomId) {
        openChatRoom(mostRecentRoomId);
    } else {
        console.log('No recent room ID found');
    }
}

// DOMContentLoaded 이벤트를 사용하여 페이지 로드 후 함수 호출
document.addEventListener('DOMContentLoaded', function() {
    loadChatRooms();
});



function getMostRecentRoomId() {
	// 로컬스토리지에서 가장 최근 메시지를 가진 채팅방 ID를 반환
	const chatRooms = document.querySelectorAll('#chat-room-item');
	let mostRecentRoomId = null;
	let mostRecentTimestamp = 0;

	chatRooms.forEach(function(room) {
		const roomId = room.getAttribute('data-room-id');
		const lastMessage = getLastMessageFromLocalStorage(roomId);

		if (lastMessage && new Date(lastMessage.timestamp).getTime() > mostRecentTimestamp) {
			mostRecentTimestamp = new Date(lastMessage.timestamp).getTime();
			mostRecentRoomId = roomId;
		}
	});

	return mostRecentRoomId;
}

function getLastMessageFromLocalStorage(roomId) {
	const messages = JSON.parse(localStorage.getItem(`chatMessages_${roomId}`)) || [];
	return messages[messages.length - 1];
}

function openChatRoom(roomId) {
	const currentRoomId = document.getElementById('room-id').value;

	if (currentRoomId === roomId) {
		console.log('같은 채팅방입니다.');
		return;
	}

	// 메시지 ID 세트를 초기화
	window.messageIds = new Set();

	document.querySelectorAll('.chat-window').forEach(function(win) {
		win.classList.remove('active');
	});

	document.querySelectorAll('#chat-room-item').forEach(function(room) {
		room.classList.remove('active');
	});

	const chatRoomList = document.getElementById('chat-room-list');
	const chatWindow = document.getElementById('chat-window');
	const chatMessages = document.getElementById('chat-messages');
	const chatRoomTitle = document.getElementById('chat-room-title');
	const roomIdInput = document.getElementById('room-id');
	const groupId = document.getElementById('group-id').value;

	if (chatRoomList && chatWindow && chatMessages && chatRoomTitle && roomIdInput) {
		// 현재 채팅방 표시
		roomIdInput.value = roomId;

		// 채팅방 목록에서 이름 가져오기
		const chatRoomItem = document.querySelector(`#chat-room-item[data-room-id='${roomId}']`);
		const roomName = chatRoomItem ? chatRoomItem.querySelector('#chat-room-name').textContent : 'Unknown Room';

		chatRoomTitle.textContent = roomName; // 채팅방 이름만 표시

		chatWindow.classList.add('active');

		// 기존 채팅 메시지 비우기
		chatMessages.innerHTML = "";

		// 로컬스토리지에서 메시지 로드
		const messages = loadMessagesFromLocalStorage(roomId);
		messages.forEach(function(msg) {
			addMessageToChat(roomId, msg.sender, msg.content, new Date(msg.timestamp), msg.messageId, msg.memberProfilePict);
		});

		// 서버에서 멤버 목록 로드
		$.ajax({
			url: `${document.body.dataset.contextPath}/group/${groupId}/chat/members/${roomId}`,
			method: 'GET',
			success: function(members) {
				updateMemberList(members);
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.error('채팅방 멤버 목록을 가져오는 중 오류 발생:', textStatus, errorThrown);
			}
		});
		updateMemberCount(roomId);
	}
}

function loadGroupMembers() {
	const contextPath = document.body.dataset.contextPath;
	const groupId = document.getElementById('group-id').value;
	const url = `${contextPath}/group/${groupId}/chat/groupMembers`;

	$.ajax({
		url: url,
		method: 'GET',
		success: function(data) {
			const addRoomMembersContainer = document.getElementById('group-members');
			addRoomMembersContainer.innerHTML = ''; // 기존 내용을 비움

			const userId = document.getElementById('user-id').value;

			data.forEach(function(member) {
				// 본인 제외
				if (member.memberId !== userId) {
					const memberElement = document.createElement('div');
					memberElement.innerHTML = `
                        <input type="checkbox" id="member-${member.memberId}" name="groupMembers" value="${member.memberId}">
                        <label for="member-${member.memberId}">${member.memberName}</label>
                    `;
					addRoomMembersContainer.appendChild(memberElement);
				}
			});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.error('그룹원 목록을 가져오는 중 오류 발생:', textStatus, errorThrown);
			alert('그룹원 목록을 가져오는 중 오류가 발생했습니다.');
		}
	});
}
function setupAddRoomModal() {
	const addRoomBtn = document.getElementById('add-room-btn');
	const closeModalBtn = document.getElementById('close-modal-btn');
	const createRoomBtn = document.getElementById('create-room-btn');
	const addRoomModal = document.getElementById('add-room-modal');
	const contextPath = document.body.dataset.contextPath;

	if (addRoomBtn) {
		addRoomBtn.addEventListener('click', function() {
			addRoomModal.style.display = 'block';
			loadGroupMembers(); // 그룹원 목록을 로드
		});
	}

	if (closeModalBtn) {
		closeModalBtn.addEventListener('click', function() {
			addRoomModal.style.display = 'none';
		});
	}

	let isCreatingRoom = false;

	createRoomBtn.addEventListener('click', function() {
		createRoomBtn.disabled = true; // 버튼 비활성화

		const roomName = document.getElementById('room-name').value.trim();
		if (roomName === '') {
			Swal.fire({
				icon: 'warning',
				title: '채팅방 제목을 입력해주세요!',
				confirmButtonText: '확인',
			}).then((result) => {
				if (result.isConfirmed) {
					// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션
					createRoomBtn.disabled = false; // 버튼 활성화
					return;

				}
			});
		}

		const selectedMembers = Array.from(document.querySelectorAll('#group-members input:checked')).map(input => input.value);

		const userId = document.getElementById('user-id').value;
		const groupId = document.getElementById('group-id').value;
		if (!selectedMembers.includes(userId)) {
			selectedMembers.push(userId);
		}

		if (selectedMembers.length === 0) {
			alert('추가할 멤버를 선택하세요.');
			createRoomBtn.disabled = false; // 버튼 활성화
			return;
		}

		const payload = {
			roomName: roomName,
			members: selectedMembers
		};

		$.ajax({
			url: `${contextPath}/group/${groupId}/chat/create`,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(payload),
			success: function(response) {
				const roomId = response.roomId;
				console.log('Created room ID:', roomId);
				console.log('Selected members:', selectedMembers);

				$.ajax({
					url: `${contextPath}/group/${groupId}/chat/addMembers`,
					method: 'POST',
					contentType: 'application/json',
					data: JSON.stringify({
						chattingRoomId: roomId,
						members: selectedMembers
					}),
					success: function() {
						Swal.fire({
							icon: 'success',
							title: '채팅방이 생성되었습니다.',
							confirmButtonText: '확인',
						}).then((result) => {
							if (result.isConfirmed) {
								// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션

								addRoomModal.style.display = 'none';
								location.reload();
							}
						});
					},
					error: function(error) {
						console.error('멤버 추가 중 오류 발생:', error);
						alert('멤버 추가 중 오류가 발생했습니다.');
					}
				});
			},
			error: function(error) {
				console.error('채팅방 생성 중 오류 발생:', error);
			},
			complete: function() {
				createRoomBtn.disabled = false; // 버튼 활성화
			}
		});
	});


}

function updateMemberCount(roomId) {
	const contextPath = document.body.dataset.contextPath;
	const groupId = document.getElementById('group-id').value;
	$.ajax({
		url: `${contextPath}/group/${groupId}/chat/members/${roomId}`,
		method: 'GET',
		success: function(members) {
			const memberCount = members.length;
			document.getElementById('member-count').textContent = memberCount;
			updateMemberList(members);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.error('Error fetching members:', textStatus, errorThrown);
		}
	});
}

function setupMembersDropdown() {
	const membersIcon = document.getElementById('members-icon');
	const membersDropdown = document.getElementById('members-dropdown');

	membersIcon.addEventListener('click', function() {
		if (membersDropdown.style.display === 'block') {
			membersDropdown.style.display = 'none';
		} else {
			membersDropdown.style.display = 'block';
			const roomId = document.getElementById('room-id').value;
			updateMemberCount(roomId); // 멤버 목록을 업데이트합니다.
		}
	});

	// 페이지의 다른 부분을 클릭하면 드롭다운 메뉴를 숨깁니다.
	window.addEventListener('click', function(event) {
		if (!membersIcon.contains(event.target) && !membersDropdown.contains(event.target)) {
			membersDropdown.style.display = 'none';
		}
	});
}
function updateMemberList(members) {
	const membersList = document.getElementById('members-list');
	membersList.innerHTML = ''; // 기존 목록을 지웁니다.

	// Set을 사용하여 중복된 멤버 제거
	const uniqueMembers = new Map();

	members.forEach(function(member) {
		// Map의 키를 멤버 ID로 사용하여 중복된 ID를 제거
		if (!uniqueMembers.has(member.memberId)) {
			uniqueMembers.set(member.
				memberId, member.memberName);
		}
	});

	// 중복이 제거된 멤버 목록을 리스트에 추가
	uniqueMembers.forEach(function(memberName, memberId) {
		const memberItem = document.createElement('li');
		memberItem.textContent = memberName;
		membersList.appendChild(memberItem);
	});
}
// 메시지 전송 버튼 클릭 시 메시지 전송 로직 추가
document.getElementById('send-message-btn').addEventListener('click', () => {
	const messageInput = document.getElementById('message-input');
	const message = messageInput.value.trim();
	if (message) {
		// 여기에 메시지를 서버에 전송하는 로직 추가
		console.log(`Sending message: ${message}`);
		messageInput.value = ''; // 입력 필드 비우기
	}
});


