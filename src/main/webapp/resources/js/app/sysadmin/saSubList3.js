
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


// lineChart statistics Chart
// --------------------------------------------------------------------
let href1 = `${gitdozicPath}/saSub/rest`;
let paymentDetailBox = document.querySelector('.paymentDetailBox');
const lineChartEl = document.querySelector('#lineChart'),
   lineChartConfig = {
     
     chart: {
       height: 250,
       type: 'line',
       parentHeightOffset: 0,
       zoom: {
         enabled: false
       },
       toolbar: {
         show: false
       },
       events: {
        click: function (event, chartContext, opts) {
          let dataPoint = opts.dataPointIndex;   // 내가 누른 포인트
          let clickedMonth = chartContext.axes.w.globals.categoryLabels[dataPoint];
          let monthChange = document.querySelector('.monthChange');
          let clickMonth = clickedMonth.replace(/[^0-9]/g, "").padStart(2, "0");
          let clickYear = "2024";
          console.log(clickMonth);
          
          // clickMonth를 사용하여 Date 객체 생성
          let last = new Date(clickYear, clickMonth, 0).getDate();
          let startDate = "2024-" + clickMonth + "-" + "01";
          let lastDate = "2024-" + clickMonth + "-" + last;
          console.log(lastDate);
          console.log(startDate);
          axios.get(`${href1}/payment`, {
            params: {
              startDate: startDate,
              lastDate: lastDate
            }
          })
          .then(resp => {
			 monthChange.textContent = clickedMonth;
             const paymentDetailList = resp.data.target;
			 // 상세보기 자동으로 펼쳐짐
			 // 아코디언 자동으로 열기
			 const accordionButton = document.querySelector('.showPayDetail1');
			 const accordionCollapse = document.querySelector('.showPayDetail2');
			 
			 accordionButton.classList.remove('collapsed');  // collapsed 클래스 제거
			 accordionButton.setAttribute('aria-expanded', 'true');  // aria-expanded를 true로 설정
			 accordionCollapse.classList.add('show');  // 아코디언을 열기 위해 show 클래스 추가
			 
	         console.log("응답", paymentDetailList);
             let html = '';
             if (Array.isArray(paymentDetailList) && paymentDetailList.length > 0) {
               for (let detail of paymentDetailList) {
	         
                 let joinDate = new Date(detail['PAYMENT_DATE']);
                 let formattedDate = joinDate.getFullYear() + '-' +
                                     String(joinDate.getMonth() + 1).padStart(2, '0') + '-' +
                                     String(joinDate.getDate()).padStart(2, '0');
             
                 html += `
			 	 <tr>
			 	   <td>
			 	     <a class="sysadmin-list"><span class="fw-medium">${detail['GROUP_ID']}</span></a>
			 	   </td>
			 	   <td>
			 	     <span class="fw-medium">${detail['GROUP_NAME']}</span>
			 	   </td>
			 	   <td>
			 	     <span class="fw-medium">${detail['PAYMENT_AMOUNT']}원</span>
			 	   </td>
			 	   <td class="fw-medium">${formattedDate}</td>
			 	 </tr>
                 `;
            }
			paymentDetailBox.innerHTML = html;
            }else{
	           html += `
                      <tr>
                        <td class="mt-2" colspan="6" style="text-align:center; padding-top:20px;">
                          조회된 구독데이터가 없습니다.
                        </td>
                      </tr>
               `;
			   paymentDetailBox.innerHTML = html;
            }
          });
        }
      }
     },
     series: [
       {
         data: paymentAmount
       }
     ],
     markers: {
       strokeWidth: 7,
       strokeOpacity: 1,
       strokeColors: [cardColor],
       colors: [config.colors.warning]
     },
     dataLabels: {
       enabled: false
     },
     stroke: {
       curve: 'straight'
     },
     colors: [config.colors.warning],
     grid: {
       borderColor: borderColor,
       xaxis: {
         lines: {
           show: true
         }
       },
       padding: {
         top: -20
       }
     },
     tooltip: {
       custom: function ({ series, seriesIndex, dataPointIndex, w }) {
        return '<div class="px-3 py-2">' + '<span>' + series[seriesIndex][dataPointIndex] + '원</span>' + '</div>';
      }
     },
     xaxis: {
       categories: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
       axisBorder: {
         show: false
       },
       axisTicks: {
         show: false
       },
       labels: {
         style: {
           colors: labelColor,
           fontSize: '13px'
         }
       }
     },
     yaxis: {
       labels: {
         style: {
           colors: labelColor,
           fontSize: '13px'
         }
       }
     }
   };
   if (typeof lineChartEl !== undefined && lineChartEl !== null) {
   const lineChart = new ApexCharts(lineChartEl, lineChartConfig);
   lineChart.render();
   }


// Line Chart2
  // --------------------------------------------------------------------
  let lineChart2;
  const lineChartEl2 = document.querySelector('#lineChart2'),
    lineChartConfig2 = {
      chart: {
        height: 250,
        type: 'line',
        parentHeightOffset: 0,
        zoom: {
          enabled: false
        },
        toolbar: {
          show: false
        }
      },
      series: [
        {
          data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100]
        }
      ],
      markers: {
        strokeWidth: 7,
        strokeOpacity: 1,
        strokeColors: [cardColor],
        colors: [config.colors.warning]
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'straight'
      },
      colors: [config.colors.warning],
      grid: {
        borderColor: borderColor,
        xaxis: {
          lines: {
            show: true
          }
        },
        padding: {
          top: -20
        }
      },
      tooltip: {
        custom: function ({ series, seriesIndex, dataPointIndex, w }) {
          return '<div class="px-3 py-2">' + '<span>' + series[seriesIndex][dataPointIndex] + '%</span>' + '</div>';
        }
      },
      xaxis: {
        categories: [
          '7/12',
          '8/12',
          '9/12',
          '10/12',
          '11/12',
          '12/12',
          '13/12',
          '14/12',
          '15/12',
          '16/12',
          '17/12',
          '18/12',
          '19/12',
          '20/12',
          '21/12'
        ],
        axisBorder: {
          show: false
        },
        axisTicks: {
          show: false
        },
        labels: {
          style: {
            colors: labelColor,
            fontSize: '13px'
          }
        }
      },
      yaxis: {
        min: 0,  // y축의 최소값을 0으로 설정
        max: 100,  // y축의 최대값을 100으로 설정
        forceNiceScale: false,  // 자동 스케일링을 강제로 비활성화
        tickAmount: 4,  // 눈금을 4개로 제한 (0, 25, 50, 75, 100)
        labels: {
          style: {
            colors: labelColor,
            fontSize: '13px'
          }
        }
      }
    };
  if (typeof lineChartEl2 !== undefined && lineChartEl2 !== null) {
    lineChart2 = new ApexCharts(lineChartEl2, lineChartConfig2);
    lineChart2.render();
  }


// weeklyEarningReports
// --------------------------------------------------------------------

let totalDiv = document.querySelector(".totalDiv");
let paymentGroupNone = document.querySelector(".paymentGroupNone");
let percentagePay = document.querySelector(".percentagePay");

// .groupDropDown 내부의 .dropdown-item 항목을 선택하고 클릭 이벤트 추가(다른 .dropdown-item 항목과 헷갈리지 않기 위함)
document.querySelectorAll('.groupDropDown .dropdown-item').forEach(function(item) {
   item.addEventListener('click', function(event) {
	  const accordionButton2 = document.querySelector('.showPayDetail3');
	  const accordionCollapse2 = document.querySelector('.showPayDetail4');
	  
	  accordionButton2.classList.remove('collapsed');  // collapsed 클래스 제거
	  accordionButton2.setAttribute('aria-expanded', 'true');  // aria-expanded를 true로 설정
	  accordionCollapse2.classList.add('show');  // 아코디언을 열기 위해 show 클래스 추가
      const closestDropdownItem = event.target.closest('.dropdown-item');
      lineChartEl2.style.display = 'block';
      paymentGroupNone.style.display = 'none';
      if (closestDropdownItem) {
        const groupId = closestDropdownItem.getAttribute('data-id-value');
        console.log('Clicked group ID:', groupId);
        let boxElement = event.target.closest(".dropdown").querySelector(".dropdown-toggle");
        boxElement.innerText = event.target.innerHTML;

        //선택된 날짜의 신규 가입 및 구독 디테일 요청
        axios({
           url : `${href1}/total/${groupId}`,
           method : "get",
           header : {
           	"Content-Type" : "application/json"
           }
        })
        .then(resp => {
          const totalData = resp.data.target;
          const payTotalByGroup = resp.data.target2;
	      console.log("구독 유지율 응답 전체", payTotalByGroup);
          if(payTotalByGroup[0].TOTAL_PAYMENTS == 0){
	          lineChartEl2.style.display = 'none';
              paymentGroupNone.style.display = 'block';
              paymentGroupNone.innerHTML = "조회된 구독 데이터가 없습니다.";
	          console.log("0입니다");
          }
          if(payTotalByGroup[0].SUBSCRIPTION_RATE === null || payTotalByGroup[0].SUBSCRIPTION_RATE === 'null' || typeof payTotalByGroup[0].SUBSCRIPTION_RATE === 'undefined'){
			  percentagePay.innerHTML = "0%";
          }else{	
          	  percentagePay.innerHTML = payTotalByGroup[0].SUBSCRIPTION_RATE + "%";
          }
          percentagePay.style.display = 'block';
          
          let monthlyDates = [];
          let payData = [];
          for (let pay of payTotalByGroup) {
            monthlyDates.push(pay.MONTH);  // 각 MONTH 값을 배열에 추가
            if(pay.MISSING_MONTH=='missing_month'){
				payData.push(0);
            }else{
				payData.push(100);
			}
          }
          console.log("달 출력", monthlyDates);
         

          if (lineChart2) {
            lineChart2.updateSeries([{
              data: payData
            }]);
            lineChart2.updateOptions({
              xaxis: {
                categories: monthlyDates
              }
            });
          }

          let html = '';
            
          html += `
          <ul class="p-0 m-0">
            <li class="mt-2 mb-4 pb-1 d-flex justify-content-between align-items-center">
              <div class="badge bg-label-success rounded p-2"><i class="ti ti-mail ti-sm"></i></div>
              <div class="d-flex justify-content-between w-100 flex-wrap">
                <h6 class="mb-0 ms-3 fw-medium" style="font-size:1.08rem;">그룹 사원 수</h6>
                <div class="d-flex">
                  <p class="mb-0 fw-medium">${totalData['TOTAL_GROUP_MEMBER']}명</p>
                </div>
              </div>
            </li>
            <li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
              <div class="badge bg-label-info rounded p-2"><i class="ti ti-link ti-sm"></i></div>
              <div class="d-flex justify-content-between w-100 flex-wrap">
                <h6 class="mb-0 ms-3 fw-medium" style="font-size:1.08rem;">그룹 프로젝트 수</h6>
                <div class="d-flex">
                  <p class="mb-0 fw-medium">${totalData['TOTAL_GROUP_PROJECT']}개</p>
                </div>
              </div>
            </li>
            <li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
              <div class="badge bg-label-warning rounded p-2"><i class="ti ti-click ti-sm"></i></div>
              <div class="d-flex justify-content-between w-100 flex-wrap">
                <h6 class="mb-0 ms-3 fw-medium" style="font-size:1.08rem;">그룹 이슈 수</h6>
                <div class="d-flex">
                  <p class="mb-0 fw-medium">${totalData['TOTAL_GROUP_ISSUE']}개</p>
                </div>
              </div>
            </li>
            <li class="mb-2 pb-1 d-flex justify-content-between align-items-center">
              <div class="badge bg-label-primary rounded p-2"><i class="ti ti-users ti-sm"></i></div>
              <div class="d-flex justify-content-between w-100 flex-wrap">
                <h6 class="mb-0 ms-3 fw-medium" style="font-size:1.08rem;">총 구독액</h6>
                <div class="d-flex">
                  <p class="mb-0 text-success fw-medium">${totalData['TOTAL_GROUP_PAYMENT']}원</p>
                </div>
              </div>
            </li>
          </ul>
          `;
          totalDiv.innerHTML = html;
        });
      }
   });
});
});