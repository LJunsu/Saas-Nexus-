const gitdozicPath = document.body.dataset.contextPath;
$(document).ready(function() {
	
  let cardColor, headingColor, labelColor, borderColor, legendColor;

  cardColor = config.colors.cardColor;
  headingColor = config.colors.headingColor;
  labelColor = config.colors.textMuted;
  legendColor = config.colors.bodyColor;
  borderColor = config.colors.borderColor;

  const chartColors = {
    column: {
      series1: '#826af9',
      series2: '#d2b0ff',
      bg: '#f8d3ff'
    },
    donut: {
      series1: '#fee802',
      series2: '#3fd0bd',
      series3: '#826bf8',
      series4: '#2b9bf4'
    },
    area: {
      series1: '#29dac7',
      series2: '#60f2ca',
      series3: '#a5f8cd'
    },
    line: {
      series1: config.colors.primary || '#FF4560',  // 기본값 설정
      series2: config.colors.secondary || '#008FFB' // 기본값 설정
    },
    bar: {
      series1: config.colors.primary,
      series2: '#7367F0CC',
      series3: '#7367f099'
    }
  };


// Shipment statistics Chart(서비스 가입 및 구독율)
// --------------------------------------------------------------------
let href1 = `${gitdozicPath}/saSub/rest`;
var date = new window.Date();
let justYearMonth = date.getFullYear() + "-" + String(date.getMonth() + 1).padStart(2, "0");
let startDate = justYearMonth + "-01";
let lastDate = justYearMonth + "-30";
var last = 30;
var resultYear = null;
var resultMonth = null;
let selectedDate = null;
let goToControllerDate = null;

let dropdownMenu = document.querySelectorAll(".dropdown-item");
let memDetailBox = document.querySelector(".memDetailBox");
let memDetailBox2 = document.querySelector(".memDetailBox2");
let shipment;  // 차트 변수 선언

// 두 리스트의 합 구하기
let summedData2 = memCountByRegDt.map((value, index) => value + subCountByRegDt[index]);
console.log("summedData2", summedData2);
	
// 합에서 최대값 구하기
let maxSummedValue2 = Math.max(...summedData2);
console.log("maxSummedValue2", maxSummedValue2);

const shipmentEl = document.querySelector('#shipmentStatisticsChart'),
  shipmentConfig = {
    series: [
      {
        name: '신규 가입자수',
        type: 'column',
        data: memCountByRegDt
      },
      {
        name: '신규 구독자수',
        type: 'line',
        data: subCountByRegDt
      }
    ],
    chart: {
      height: 350,
      type: 'line',
      stacked: false,
      parentHeightOffset: 0,
      toolbar: {
        show: false
      },
      zoom: {
        enabled: false
      },
      events: {
        dataPointSelection: function (event, chartContext, config) {
          // xaxis의 index를 사용하여 선택된 날짜 가져오기
          selectedDate = config.w.globals.categoryLabels[config.dataPointIndex];
          goToControllerDate = justYearMonth + "-" + String(selectedDate).padStart(2, "0");
          console.log('선택된 날짜:', goToControllerDate);

          // 상세보기 자동으로 펼쳐짐
          const accordionItem = document.querySelector('.showMemDetail1');
          accordionItem.classList.add('active');
          const accordionCollapse = accordionItem.querySelector('.showMemDetail2');
          accordionCollapse.classList.add('show');

          //선택된 날짜의 신규 가입 및 구독 디테일 요청
          axios({
             url : `${href1}/${goToControllerDate}`,
             method : "get",
             header : {
             	"Content-Type" : "application/json"
             }
          })
          .then(resp => {
	        const detailList = resp.data.target;
	        const subDetailList = resp.data.target2;
          	console.log("신규가입 상세데이터", detailList);
          	console.log("신규가입 상세데이터2", subDetailList);
            let html = '';
	        let html2 = '';
            
            //신규가입 디테일 출력
            if (Array.isArray(detailList) && detailList.length > 0) {
              for (let detail of detailList) {
	          
                  let joinDate = new Date(detail.memberJoinDate);
                  let formattedDate = joinDate.getFullYear() + '-' +
                                      String(joinDate.getMonth() + 1).padStart(2, '0') + '-' +
                                      String(joinDate.getDate()).padStart(2, '0');

	              let badge = '';
                  if (detail.memberRole === 'ROLE_USER') {
                      badge = '<span class="badge bg-label-secondary me-1">멤버</span>';
                  } else if (detail.memberRole === 'ROLE_GROUPADMIN') {
                      badge = '<span class="badge bg-label-success me-1">관리자</span>';
                  }
              
                  html += `
                  <tr>
                        <td>
                          <span class="badge bg-label-warning me-1">신규회원</span>
                        </td>
                        <td>
                          <a class="sysadmin-list"><span class="fw-medium">${detail.memberId}</span></a>
                        </td>
                        <td>
                          <span class="fw-medium">${detail.memberName}</span>
                        </td>
                        <td>
                          <span class="fw-medium">${detail.memberEmail}</span>
                        </td>
                        <td class="fw-medium">${formattedDate}</td>
                        <td>
                          ${badge}
                        </td>
                      </tr>
               `;
              }
            memDetailBox.innerHTML = html;
            }else{
	           html += `
                  <tr>
                        <td class="mt-2" colspan="6" style="text-align:center; padding-top:20px;">
                          조회된 신규 회원데이터가 없습니다.
                        </td>
                      </tr>
               `;
                memDetailBox.innerHTML = html;
            }

            //신규구독 디테일 출력
            if (subDetailList[0] != null) {
              for (let detail of subDetailList) {
	            let badge = '';
                if (detail['APPROVAL_DATE']==null) {
                    badge = '<span class="badge bg-label-danger me-1">승인 대기</span>';
                } else {
                    badge = '<span class="badge bg-label-success me-1">승인 완료</span>';
                }
	
                let joinDate = new Date(detail['PAYMENT_DATE']);
                let formattedDate = joinDate.getFullYear() + '-' +
                                    String(joinDate.getMonth() + 1).padStart(2, '0') + '-' +
                                    String(joinDate.getDate()).padStart(2, '0');

                html2 += `
                    <tr>
                      <td>
                        <span class="badge bg-label-primary me-1">신규구독</span>
                      </td>
                      <td>
                        <a class="sysadmin-list"><span class="fw-medium">${detail['GROUP_NAME']}</span></a>
                      </td>
                      <td>
                        <span class="fw-medium">${detail['MEMBER_NAME']}</span>
                      </td>
                      <td>
                        <span class="fw-medium">${detail['GROUP_ADDRESS']}</span>
                      </td>
                      <td class="fw-medium">${formattedDate}</td>
                      <td>
                        ${badge}
                      </td>
                    </tr>
                `;
            }
            memDetailBox2.innerHTML = html2;
          }else{
	         html2 += `
                    <tr>
                      <td class="mt-2" colspan="6" style="text-align:center; padding-top:20px;">
                        조회된 신규 구독데이터가 없습니다.
                      </td>
                    </tr>
             `;
              memDetailBox2.innerHTML = html2;
          }
          })
        }
      }
    },
    markers: {
      size: 4,
      colors: ['#fff'], // 기본 색상
      strokeColors: '#77B6EA', // 선 색상
      hover: {
        size: 6
      },
      borderRadius: 4
    },
    stroke: {
      curve: 'smooth',
      width: [0, 3],
      lineCap: 'round'
    },
    legend: {
      show: true,
      position: 'bottom',
      markers: {
        width: 8,
        height: 8,
        offsetX: -3
      },
      height: 40,
      offsetY: 10,
      itemMargin: {
        horizontal: 10,
        vertical: 0
      },
      fontSize: '15px',
      fontFamily: 'Public Sans',
      fontWeight: 400,
      labels: {
        useSeriesColors: false
      },
      offsetY: 10
    },
    grid: {
      strokeDashArray: 8
    },
    fill: {
      opacity: [1, 1]
    },
    plotOptions: {
      bar: {
        columnWidth: '30%',
        borderRadius: 4
      }
    },
    dataLabels: {
      enabled: false
    },
    xaxis: {
      tickAmount: last,
      categories: Array.from({ length: last }, (_, i) => (i + 1).toString()), // 1일부터 30일까지 카테고리
      labels: {
        style: {
          fontSize: '13px',
          fontFamily: 'Pretendard',
          fontWeight: 400
        }
      },
      axisBorder: {
        show: false
      },
      axisTicks: {
        show: false
      }
    },
    yaxis: {
      tickAmount: 4,
      min: 0,
      max: maxSummedValue2,
      labels: {
        formatter: function (val) {
          return val + '명';
        }
      }
    }
  };
// 차트 초기 렌더링
if (shipmentEl) {
  shipment = new ApexCharts(shipmentEl, shipmentConfig);
  shipment.render();
}

// 드롭다운 메뉴 이벤트 리스너
dropdownMenu.forEach((menu) => {
  menu.addEventListener('click', e => {
	if (!menu.closest('.groupDropDown')) {
    let boxElement = e.target.closest(".dropdown").querySelector(".dropdown-toggle");
    boxElement.innerText = e.target.innerHTML;

    resultYear = document.querySelector(".selectYear").innerText.replace(/[^0-9]/g, "");
    resultMonth = String(document.querySelector(".selectMonth").innerText.replace(/[^0-9]/g, "")).padStart(2, "0");

    last = new Date(resultYear, resultMonth, 0).getDate();
    justYearMonth = resultYear + "-" + resultMonth;
    startDate = resultYear + "-" + resultMonth + "-01";
    lastDate = resultYear + "-" + resultMonth + "-" + last;

    axios.get(href1, {
      params: {
        justYearMonth: justYearMonth,
        startDate: startDate,
        lastDate: lastDate
      }
    })
    .then(data => {
      console.log("성공", data);
      var dataList = data.data.target;
      var dataList2 = data.data.target2;

      let dataListMade = dataList.map(Number);
      console.log("dataListMade", dataListMade);
      let dataListMade2 = dataList2.map(Number);
      console.log("dataListMade2", dataListMade2);

	  // 두 리스트의 합 구하기
	  let summedData = dataListMade.map((value, index) => value + dataListMade2[index]);
	  console.log("summedData", summedData);
	
	  // 합에서 최대값 구하기
	  let maxSummedValue = Math.max(...summedData);
	  console.log("maxSummedValue", maxSummedValue);

      // 차트 업데이트
      shipment.updateOptions({
        xaxis: {
          categories: Array.from({ length: last }, (_, i) => (i + 1).toString()), // last에 맞게 카테고리 길이 동적으로 변경
          tickAmount: last // 마지막 날짜에 맞게 틱 수 업데이트
        },
        series: [
          {
            name: '신규 가입자수',
            type: 'column',
            data: dataListMade
          },
          {
            name: '신규 구독자수',
            type: 'line',
            data: dataListMade2
          }
        ],
	    yaxis: {
		  tickAmount: 4,
          min: 0,
	      max: maxSummedValue
	    }
      });
    })
    .catch((error) => {
      console.error('Error:', error);
    });
   }
  });
});
});