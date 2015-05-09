<?php

/**
 * Deal with the request of performance data.
 * There are three params in URL, location, date and duration.
 * The php file would send JSON format data according to
 * the request.
 *
 * The response JSON format is:
 * {
 * performance:[JSON ARRAY]
 * (The items in the array)
 * ...pid:"performance_id"; time:"performance_date"; musician:"xxxxxx";
 * ...description:"xxxxxxx"; city:"city_name";
 * ...place:
 * ......{pname:"place_name", lat:latitude, lng:longitude}
 * }
 *
 */

require_once('config.php');
$error_msg = "";

$dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
mysqli_set_charset($dbc, 'utf8');
if (!$dbc) {
    die('Could not connect: ' . mysql_error());
}

// Grab the basic data from url
$location = $_REQUEST['location'];
$date = $_REQUEST['date'];
$duration = $_REQUEST['duration'];
$time_query = "";

//Make query sentence of the time
if ($duration == 'week') {
    $date1 = str_replace('-', '/', $date);
    $dateValue = date('Y-m-d', strtotime($date1 . "+7 days"));
    $time_query = "time between '$date' and '$dateValue'";
} elseif ($duration == 'month') {
    $date1 = str_replace('-', '/', $date);
    $dateValue = date('Y-m-d', strtotime($date1 . "+30 days"));
    $time_query = "time between '$date' and '$dateValue'";
} else {
    $time_query = "time > '$date'";
}

//query the place information
$query_1 = "SELECT * from place where city_id =(SELECT city_id FROM city WHERE city_name= '$location');";
$data_place = mysqli_query($dbc, $query_1);

//Construct JSON format data
$arr = array();
while ($row = mysqli_fetch_array($data_place)) {
    $query_2 = "SELECT * from performance where place_id = " . $row['place_id'] . " AND $time_query ;";
    $data_performance = mysqli_query($dbc, $query_2);
    $row_2 = mysqli_fetch_array($data_performance);

    if ($row_2) {
        $place = array("pname" => $row['place_name'], "lat" => (double)$row['lat'], "lng" => (double)$row['lng']);
        $list_item = array("place" => $place, "time" => $row_2['time'], "musician" => $row_2['musician'],
            "description" => $row_2['description'], "city"=> $location, "pid" => $row_2['performance_id']);
        array_push($arr, $list_item);
    }

}
$result = array("performance" => $arr);
echo json_encode($result);

mysqli_close($dbc);
?>
