<?php
/**
 * Created by PhpStorm.
 * User: thomas
 * Date: 15-5-6
 * Time: 下午3:02
 *
 * This is the php file which deals with operations on the
 * favourite table.
 *
 * The format of POST information is:
 * 1, tag:"like"; uuid:"$unique_id"; pid:"$performance_id";
 * 2, tag:"delete"; uuid:"$unique_id"; pid:"$performance_id";
 *
 * The response of the php file is:
 * 1,like tag:"like"
 * a) error:FALSE;
 * b) error:TRUE; error_msg:"xxxxxx";
 *
 * 2,delete tag:"delete"
 * a) error:FALSE
 * b) error:TRUE; error_msg:"xxxxxx";
 *
 */
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    $tag = $_POST['tag'];

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