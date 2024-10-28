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

// Scatter Chart
// --------------------------------------------------------------------

  const scatterChartEl = document.querySelector('#scatterChart');
  const scatterChartConfig = {
    chart: {
      height: 400,
      type: 'scatter',
      zoom: {
        enabled: true,
        type: 'xy'
      },
      parentHeightOffset: 0,
      toolbar: {
        show: false
      }
    },
    grid: {
      borderColor: borderColor,
      xaxis: {
        lines: {
          show: true
        }
      }
    },
    legend: {
      show: true,
      position: 'top',
      horizontalAlign: 'start',
      labels: {
        colors: legendColor,
        useSeriesColors: false
      }
    },
    colors: [config.colors.warning, config.colors.primary, config.colors.success],
    series: [
      {
        name: 'Angular',
        data: [
          [5.4, 170],
          [5.4, 100],
          [5.7, 110],
          [5.9, 150],
          [6.0, 200],
          [6.3, 170],
          [5.7, 140],
          [5.9, 130],
          [7.0, 150],
          [8.0, 120],
          [9.0, 170],
          [10.0, 190],
          [11.0, 220],
          [12.0, 170],
          [13.0, 230]
        ]
      },
      {
        name: 'Vue',
        data: [
          [14.0, 220],
          [15.0, 280],
          [16.0, 230],
          [18.0, 320],
          [17.5, 280],
          [19.0, 250],
          [20.0, 350],
          [20.5, 320],
          [20.0, 320],
          [19.0, 280],
          [17.0, 280],
          [22.0, 300],
          [18.0, 120]
        ]
      },
      {
        name: 'React',
        data: [
          [14.0, 290],
          [13.0, 190],
          [20.0, 220],
          [21.0, 350],
          [21.5, 290],
          [22.0, 220],
          [23.0, 140],
          [19.0, 400],
          [20.0, 200],
          [22.0, 90],
          [20.0, 120]
        ]
      }
    ],
    xaxis: {
      tickAmount: 10,
      axisBorder: {
        show: false
      },
      axisTicks: {
        show: false
      },
      labels: {
        formatter: function (val) {
          return parseFloat(val).toFixed(1);
        },
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

  if (scatterChartEl !== null) {
    const scatterChart = new ApexCharts(scatterChartEl, scatterChartConfig);
    scatterChart.render();
  }

// Horizontal Bar Chart
  // --------------------------------------------------------------------
  const horizontalBarChartEl = document.querySelector('#horizontalBarChart'),
    horizontalBarChartConfig = {
      chart: {
        height: 400,
        type: 'bar',
        toolbar: {
          show: false
        }
      },
      plotOptions: {
        bar: {
          horizontal: true,
          barHeight: '30%',
          startingShape: 'rounded',
          borderRadius: 8
        }
      },
      grid: {
        borderColor: borderColor,
        xaxis: {
          lines: {
            show: false
          }
        },
        padding: {
          top: -20,
          bottom: -12
        }
      },
      colors: config.colors.info,
      dataLabels: {
        enabled: false
      },
      series: [
        {
	      name: '누적 사용수',
          data: [500, 350, 400, 440, 210, 500, 210, 110, 90, 150]
        }
      ],
      xaxis: {
        categories: ['이슈' , '타임라인', '일정 및 이슈관리', '칸반보드', '공유 클라우드'
                    , '프로젝트 파일공유', '코드쉐어', '프로젝트 노트', '화상회의', '공지사항'],
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
  if (typeof horizontalBarChartEl !== undefined && horizontalBarChartEl !== null) {
    const horizontalBarChart = new ApexCharts(horizontalBarChartEl, horizontalBarChartConfig);
    horizontalBarChart.render();
  }
});