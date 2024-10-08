
const commentContents = document.getElementById("commentContents");
const introductionAdd = document.getElementById("introduction_add");
const introductionDelete = document.getElementById("introduction_delete");
const introduces_text = document.getElementById("introduces_text");

// 프로필 파일 사진관련
const profileAdd = document.getElementById("profile_picture_add");
const profilePictureDelete = document.getElementById("profile_picture_delete");

// sns url 관련
const profileSnsDelete = document.getElementById("profile_sns_delete");
const profileSnsAdd = document.getElementById("profile_sns_add");
const snsUrl = document.getElementById("snsUrl");
const snsUrlRegexp = /^(http|https):\/\/[\w\-]+(\.[\w\-]+)+[/#?]?.*$/;

let profileSnsId = profileSnsAdd.getAttribute("data-sns-member_id");
let introductionId = introductionAdd.getAttribute("data-member-id");
let introducesId = introductionAdd.getAttribute("data_member_id");
let profileId = profileAdd.getAttribute("data-member-id");

let profileDrop = profilePictureDelete.getAttribute("data-member-delete-id")

const resignBtn = document.getElementById("resignBtn")
const resignForm = document.getElementById("resignForm")

resignBtn.addEventListener("click", () => {

    let result = confirm("회원 탈퇴 하시겠습니까?")

    if (result) {
        resignForm.submit()
    }

})

// sns url관련
profileSnsDelete.addEventListener("click", () => {
    fetch("prfileSnsDelete?member_id="+profileSnsId, {
        method : "GET"
    })
    .then(res => res.text())
    .then(res => {

        if(res > 0) {
            location.href = "/member/mypage";
        }
    }).catch(error => {
        console.log("오류발생 :" +error);
    })
})

profileSnsAdd.addEventListener("click", () => {
   
    if(!snsUrlRegexp.test(snsUrl.value)) {
        alert("url 형식으로 입력해주세요.")
        return;
    }
    fetch("prfileSnsAdd", {
        method : "POST",
        headers : { "Content-type":"application/x-www-form-urlencoded" },
        body : "member_id="+profileSnsId + "&profile_sns_url=" + snsUrl.value
    })
    .then(res => res.text())
    .then(res => {

        if(res > 0) {
            location.href = "/member/mypage";
        }
    }).catch(error => {
        console.log("오류발생 :" +error);
    })
    
})

// 프로필 사진
profilePictureDelete.addEventListener("click", () => {

    fetch("profileDelete?member_id="+profileDrop, {
        method : "GET"
    })
    .then(res => res.text())
    .then(res => {

        if(res > 0){
            location.href = "/member/mypage";
        }
    }).catch(error => {
        console.log("오류발생 :" + error);
    })
})

// 자기소개
introductionAdd.addEventListener("click", () =>{
    
    console.log(commentContents.value);
    console.log(introducesId)

    // introduces_text.style.display="none";

    fetch("mypage", {
        method : "POST",
        headers : { "Content-type":"application/x-www-form-urlencoded" },
        body : "profile_about_me="+commentContents.value+"&member_id="+introducesId
        
    })
    .then(res => res.text())
    .then(res => {
        console.log("반환값 확인: "+ res)
        location.href = "/member/mypage";
    }).catch(error => {
        console.log("오류발생 :" + error);
    })
})

introductionDelete.addEventListener("click", () => {
    console.log(introducesId)
    fetch("introducesDelete?member_id="+introducesId, {
        method : "GET"
    })
    .then(res => res.text())
    .then(res => {
        console.log("반환값 확인: "+ res)
        location.href = "/member/mypage";
    }).catch(error => {
        console.log("오류발생 :" + error);
    })
    
})


