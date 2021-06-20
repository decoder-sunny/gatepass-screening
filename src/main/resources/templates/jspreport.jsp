<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Test Report</title>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet"  href="/report-support/report.css"/>
  <link rel=stylesheet  href="/report-support/codemirror.css"/>
  <link rel=stylesheet  href="/report-support/material.css"/>
  <script type="text/javascript" src="/report-support/codemirror.js"></script>
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="/report-support/fusioncharts-suite-xt/fusioncharts-suite-xt/js/fusioncharts.js"></script>
  <script type="text/javascript"
    src="/report-support/fusioncharts-suite-xt/fusioncharts-suite-xt/js/themes/fusioncharts.theme.fusion.js"></script>
  <script type="text/javascript"
    src="/report-support/fusioncharts-suite-xt/fusioncharts-suite-xt/js/themes/fusioncharts.theme.candy.js"></script>
  
</head>

<body class="m-4">

  <div class="container-fluid p-4" style="border:2px solid black;">


    <div class="row">
      <div class="col-sm-2 col-md-2">
        <div class="container ">
          <img src="/report-support/tech.png" style="height:100px;width:150px">
        </div>
      </div>
      <div class="col-sm-8 col-md-8 text-center content-center"><br>
        <h3 class="card-title font-weight-bold" style="margin-top:8%">Test Report</h3>
      </div>
      <div class="col-sm-2 col-md-2">
        <figure class="figure float-right">
          <img src="/report-support/sunny.jpg" height="100px" width="120px" class="figure-img rounded my-auto">
        </figure>
      </div>
    </div>
    <!-- ------------------------------------------------------- -->
    <div class="row">
      <div class="col-sm-1"></div>
      <div class="col-sm-10">
        <table class="table table-bordered table-striped">
          <tbody>
            <tr>
              <td colspan="1">Candidate Name : <%request.getAttribute("name"); %></td>
              <td colspan="2">Candidate Email: jetsunny25@gmail.com</td>
            </tr>
            <tr>
              <td>Date : 25-05-2021</td>
              <td>Gender: Male</td>
              <td>Contact No: 8769416461</td>
            </tr>
            <tr>
              <td colspan="2">Client Name :
                Techwise Digital Pvt Ltd
              </td>
              <td colspan="1">Position : Software Engineer</td>
            </tr>
          </tbody>
        </table>
        <div id="chart-container" width="640" height="150" class="text-center content-center"></div>
      </div>
      <div class="col-sm-1"></div>
    </div>

    <!-- ------------------------------------------------------- -->
    <br>

    <div class="row">
      <div class="col-md-12">
        <h5 class="font-weight-bold">Overall Test Interpretation</h5>
        <ul>
          <li>Overall Score :65 %</li>
          <li> Proctoring : Found Some browser/tab toggle during the test,
            might be chance of Plagiarism</li>
        </ul>
      </div>
    </div>
    <!-- ------------------------------------------------------- -->
    <br>
    <div class="row">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h4 class="text-center card-title">Total Vs Attempted Questions</h4>
            <div id="cylinder-container"></div>
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <div class="card">
          <div class="card-body my-auto">
            <h4 class="text-center card-title">Section Wise Score</h4>
            <table id="clienttables" class="table table-striped align-middle
      table-no-bordered table-hover" cellspacing="0">
              <tbody>

                <tr>
                  <th>Section </th>
                  <th>Total Marks</th>
                  <th>Obtained Marks</th>
                  <th>Percentage</th>
                </tr>
                <tr>
                  <td>Aptitude </td>
                  <td> 100</td>
                  <td> 80</td>
                  <td class="text-success">80 %</td>
                </tr>
                <tr>
                  <td>Aptitude </td>
                  <td> 100</td>
                  <td> 80</td>
                  <td class="text-success">80 %</td>
                </tr>
                <tr>
                  <td>Aptitude </td>
                  <td> 100</td>
                  <td> 80</td>
                  <td class="text-success">80 %</td>
                </tr>
                <tr>
                  <td>Aptitude </td>
                  <td> 100</td>
                  <td> 80</td>
                  <td class="text-success">80 %</td>
                </tr>
                <tr>
                  <td>Aptitude </td>
                  <td> 100</td>
                  <td> 80</td>
                  <td class="text-success">80 %</td>
                </tr>
                <tr class="font-weight-bolder">
                  <td>Total</td>
                  <td>100</td>
                  <td>80</td>
                  <td class="text-success">80 %</td>
                </tr>
              </tbody>

            </table>
          </div>
        </div>
      </div>
    </div>
    <!-- ------------------------------------------------------- -->
    <br>

    <div class="row">
      <div class="col-md-12 col-sm-12">
        <div class="card">
          <div class="card-body">
            <div id="bar-container"></div>
          </div>
        </div>
      </div>
    </div>

    <br>

    <div class="row">
      <div class="col-md-12 col-sm-12">
        <br>

        <div class="card mt-4" style="break-before:page;">
          <div class="card-header " style="background-color: #761A7EE3;">
            <h4 class="card-title">Code Problem</h4>
          </div>
          <div class="card-body">
            <h5 >This section of the test judges a candidate's problem solving and
              debugging
              skills. It measures their ability to find logical and syntactical error in the given piece
              of code and fix them.
            </h5>
            <h5 >Here a candidate has to read the code and find error in it if any. If
              found,
              he/she have to fix the error by writing the correct code.</h5>
            <div class="row">
              <div class="col-md-6 col-sm-6">
                <h5 class="text-primary">Original Code</h5>
                <textarea class="textarea-class">#include <iostream>
                  using namespace std;
                  int main() {
                      int x; 
                      cin >> x;
                      /*write your code here */
                  }
                </textarea>
              </div>

              <div class="col-md-6 col-sm-6">
                <h5 class="text-primary">Candidate's Code</h5>
                <textarea class="textarea-class">#include <iostream>
                    using namespace std;
                    int main() {
                        int x; 
                        cin >> x;
                        /*write your code here */
                    }
                </textarea>
              </div>

              <div class="col-md-2">
                <span class="badge badge-pill badge-info" style="margin-top: 20px;"> Attempts : 2</span>
              </div>
              <div class="col-md-2"><span class="badge badge-pill badge-info" style="margin-top: 20px;">
                  Time : 5 Min 0 Sec</span>
              </div>
              <div class="col-md-2"><span class="badge badge-pill badge-info" style="margin-top: 20px;">
                Test Cases : 100 % </span>
            </div>
             
            </div>
           

          </div>


        </div>

        <div class="card mt-4" style="break-before:page;">
          <div class="card-header " style="background-color: #761A7EE3;">
            <h4 class="card-title">Aptitude</h4>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3" >
                A .
                <img src="/report-support/sunny.jpg" height="80px" width="80px"/>
              </div>
              <div class="col-md-3">
                B .
                <img src="/report-support/sunny.jpg" height="80px" width="80px"/>
              </div>
              <div class="col-md-3">
                C .
                <img src="/report-support/sunny.jpg" height="80px" width="80px"/>
              </div>
              <div class="col-md-3">
               D .
                <img src="/report-support/sunny.jpg" height="80px" width="80px"/>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : A</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : A</h5>
              </div>
            </div>

          </div>


        </div>

        <div class="card mt-4" style="break-before:page;">
          <div class="card-header " style="background-color: #761A7EE3;">
            <h4 class="card-title">Aptitude</h4>
          </div>
          <div class="card-body">
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>
            <div class="row">
              <div class="col-md-1 col-sm-1">
                <h5 style="font-weight: bold;"> 1 :</h5>
              </div>
              <div class="col-md-11 col-sm-11" style="margin-left: -4%;">
                <h5 style="font-weight: 600;">How many Lok Sabha seats belong to Indian Parliament</h5>
              </div>

              <div class="col-md-3">
                <h5> Option A :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option B :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option C :243</h5>
              </div>
              <div class="col-md-3">
                <h5> Option D :243</h5>
              </div>
              <div class="col-md-6">
                <h5> Correct Answer : 243</h5>
              </div>
              <div class="col-md-6">
                <h5> Candidate
                  Response : 243</h5>
              </div>
            </div>

          </div>


        </div>



      </div>
    </div>

    <br>

    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-header " style="background-color: #761A7EE3;">
            <h4 class="card-title">Web Proctor Info</h4>
          </div>
          <div class="card-body">
            <div class="col-md-12">
              <h4 class="font-weight-300">
                This section gives an insight of the candidate's test environment which includes
                taking pictures of the candidate periodically, checking whether the candidate has switched
                browser/tab during the process of the test.
              </h4>
            </div>
            <div class="col-md-12">
              <h5 class="card-title font-weight-100 text-danger">Browser
                Toggle : 5 </h5>
            </div>
            <div class="col-md-12">
              <h5 class="card-title font-weight-100 ">
                Web Proctoring Images
              </h5>
            </div>
            <figure class="figure">
              <img src="/report-support/sunny.jpg" width="150px" height="100px" class="figure-img rounded pr-1"/>
              <figcaption class="figure-caption">Dec 19, 2020, 12:56:14 PM</figcaption>
            </figure>
            <figure class="figure">
              <img src="/report-support/sunny.jpg" width="150px" height="100px" class="figure-img rounded pr-1"/>
              <figcaption class="figure-caption">Dec 19, 2020, 12:56:14 PM</figcaption>
            </figure>
            <figure class="figure">
              <img src="/report-support/sunny.jpg" width="150px" height="100px" class="figure-img rounded pr-1"/>
              <figcaption class="figure-caption">Dec 19, 2020, 12:56:14 PM</figcaption>
            </figure>
            <figure class="figure">
              <img src="/report-support/sunny.jpg" width="150px" height="100px" class="figure-img rounded pr-1"/>
              <figcaption class="figure-caption">Dec 19, 2020, 12:56:14 PM</figcaption>
            </figure>
            <figure class="figure">
              <img src="/report-support/sunny.jpg" width="150px" height="100px" class="figure-img rounded pr-1"/>
              <figcaption class="figure-caption">Dec 19, 2020, 12:56:14 PM</figcaption>
            </figure>

          </div>
        </div>
      </div>
    </div>
    <br/>


  </div>

</body>

<script type="text/javascript" src="/report-support/report.js"></script>
</html>