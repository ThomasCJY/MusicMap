<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>DATA</title>
</head>

<?php
    require_once('config.php');
    // Clear the error message
    $error_msg = "";

    // Connect to the database

    $dbc = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
    mysqli_set_charset($dbc, 'utf8');
    if (!$dbc)
    {
        die('Could not connect: ' . mysql_error());
    }


    // Grab the basic data from url
    $location = $_REQUEST['location'];
    $date = $_REQUEST['date'];

    $query_1 = "SELECT * from place where city_id =(SELECT city_id FROM city WHERE city_name= '$location');";
    $data_place = mysqli_query($dbc, $query_1);

    $arr = array();
    while($row=mysqli_fetch_array($data_place)){
         $query_2 = "SELECT * from performance where place_id = ".$row['place_id']." AND time>'$date';";
         $data_performance = mysqli_query($dbc, $query_2);
         $row_2 = mysqli_fetch_array($data_performance);

         
         if($row_2){
            $place = array("pname"=>$row['place_name'], "lat"=>(double)$row['lat'], "lng"=>(double)$row['lng']);
            $listitem = array("place"=>$place, "time"=>$row_2['time'], "musician"=>$row_2['musician']);
            array_push($arr, $listitem);
         }

    }
    $result = array("performance"=>$arr);
    echo json_encode($result);
    
    mysqli_close($dbc);
?>