var info = [[${data}]];

var total_score = info.detail.map(x => x.totalMarks).reduce((prev, next) => prev + next);
var obtainedMarks = info.detail.map(x => x.marksObtained).reduce((prev, next) => prev + next);

var textareas = document.querySelectorAll(".textarea-class");
for (var i = 0; i < textareas.length; i++) {
  CodeMirror.fromTextArea(textareas[i], {
    lineNumbers: true,
  theme: 'material',
  mode: "text/x-java",
  readOnly: true,
  scrollbarStyle: "null"
  });
}


const dataSource1 = {
  chart: {
    numbersuffix: "%",
    gaugefillmix: "{dark-20},{light+70},{dark-10}",
    theme: "fusion"
  },
  colorrange: {
    color: [
    {
              minvalue: "0",
              maxvalue: "40",
              label: "Fail",
              code: "#FF0000"
            },
            {
              minvalue: "40",
              maxvalue: "60",
              label: "Average",
              code: "#FFC533"
            },
            {
              minvalue: "60",
              maxvalue: "70",
              label: "Good",
              code: "#87CEFA"
            },
            {
              minvalue: "70",
              maxvalue: "80",
              label: "Very Good",
              code: "#FFB6C1"
            }, {
              minvalue: "80",
              maxvalue: "100",
              label: "Excellent",
              code: "#5DE85"
            }
    ]
  },
  pointers: {
    pointer: [
      {
        value: "65"
      }
    ]
  }
};

const dataSource2 = {
  chart: {
    lowerlimit: "0",
    upperlimit: "100",
    lowerlimitdisplay: "0",
    upperlimitdisplay: "100",
    numbersuffix: " Question",
    cylfillcolor: "#5D62B5",
    plottooltext: "Attempted: 20 Questions",
    cylfillhoveralpha: "85",
    theme: "candy"
  },
  value: Math.round((obtainedMarks / total_score) * 100)
};

const dataSource3 = {
  chart: {
    labeldisplay: "auto",
          theme: "fusion",
          caption: "Marks Distribution In Each Section",
          xaxisname: "Section Name",
          yaxisname: "Marks",
          showvalues: "1",
  },
  categories: [
    {
      category: [
        {
          label: "USA"
        },
        {
          label: "GB"
        },
        {
          label: "China"
        },
        {
          label: "Russia"
        },
        {
          label: "Germany"
        },
        {
          label: "France"
        },
        {
          label: "Japan"
        },
        {
          label: "Australia"
        }
      ]
    }
  ],
  dataset: [
    {
      seriesname: "2017",
      data: [
        {
          value: "121"
        },
        {
          value: "70"
        },
        {
          value: "67"
        },
        {
          value: "55"
        },
        {
          value: "42"
        },
        {
          value: "42"
        },
        {
          value: "41"
        },
        {
          value: "29"
        }
      ]
    },
    {
      seriesname: "2016",
      data: [
        {
          value: "123"
        },
        {
          value: "71"
        },
        {
          value: "59"
        },
        {
          value: "52"
        },
        {
          value: "34"
        },
        {
          value: "32"
        },
        {
          value: "29"
        },
        {
          value: "32"
        }
      ]
    }
  ]
};


FusionCharts.ready(function() {
  var myChart1 = new FusionCharts({
    type: "hlineargauge",
    renderAt: "chart-container",
    width: "75%",
    height: "90%",
    dataFormat: "json",
    dataSource:dataSource1
  }).render();

  var myChart2 = new FusionCharts({
    type: "cylinder",
    renderAt: "cylinder-container",
    width: "100%",
    height: "400",
    dataFormat: "json",
    dataSource:dataSource2
  }).render();

  var myChart3 = new FusionCharts({
    type: "scrollcolumn2d",
    renderAt: "bar-container",
    width: "100%",
    height: "400",
    dataFormat: "json",
    dataSource:dataSource3
  }).render();

});