<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-6
 * Time: 下午3:02
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // get tag
    $tag = $_POST['tag'];

    // include db handler
    require_once 'DB_Functions.php';
    $db = new DB_Functions();

    // response Array
    $response = array("tag" => $tag, "error" => FALSE);

    $uuid = $_POST['uuid'];
    $p_id = $_POST['p_id'];

    if($tag == 'like'){

        $res = $db->insertFavourite($uuid, $p_id);

        if($res){
            echo json_encode($response);
        }else{
            $response["error"] = TRUE;
            $response["error_msg"] = "Insert repeatedly!";
            echo json_encode($response);
        }
    }

    //When tag == 'delete';
    else {

        $res = $db->deleteFavourite($uuid, $p_id);

        if($res == 1){
            echo json_encode($response);
        }else{
            $response["error"] = TRUE;
            $response["error_msg"] = "No match Information!";
            echo json_encode($response);
        }
    }


}else{
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter 'tag' is missing!";
    echo json_encode($response);
}

?>