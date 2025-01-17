import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SessionManager from "./SessionManager";
import {Link, useNavigate} from "react-router-dom";

function CashConversion() {
    const { isLoggedIn, setIsLoggedIn} = SessionManager(); // 세션 상태를 관리
    const [amount, setAmount] = useState(''); // 입력한 금액을 상태로 관리
    const [point, setPoint] = useState('');
    const navigate = useNavigate();
    const [loadData, setLoadData] = useState(true);
    const [dataList, setDataList] = useState([]);

    // 현금화 요청을 보내는 함수
    const handleCashConversion = () => {
        if(amount<10000 || amount % 1000 !=0) {
            alert("[조건 불충족] \n 1. 10,000원 이상\n 2. 1,000원 단위")
        }
        else if(point<amount) {
            alert("보유 포인트가 부족합니다.")
        }
        else {
            window.confirm(amount+"포인트를 사용하여 \n"+parseInt((parseInt(amount)*0.9))+"원을 신청하시겠습니까?")
            axios.post("/cash/convert", amount)
                .then(response => {
                    setLoadData(true)
                    alert("신청 완료되었습니다.")
                    // window.location.reload()
                }).catch(error=>{
            })
        }
    }
    useEffect(() => {
        if (isLoggedIn && loadData) {
            axios.get('/point')
                .then(response => {
                    setPoint(response.data)
                })
                .catch(error => {
                    console.error('데이터 요청 중 오류 발생:', error);
                });
            //신청내역 가져오기
            axios.get("/cash/convertList")
                .then(response=>{
                    setDataList(response.data);
                    setLoadData(false)
                })
                .catch((error) => {
                    console.error(error);
                });
        }
    },[isLoggedIn,loadData]);

    return (
        <div>
            {isLoggedIn ? (
                // 로그인 상태인 경우
                <div>
                    <h2>현금화</h2>
                    <input
                        type="number"
                        placeholder="금액을 입력하세요"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                    <button onClick={handleCashConversion}>현금화 요청</button>
                    <p>현금화 신청은 10,000포인트 이상부터 가능합니다.</p>
                    <p>보유 포인트: {point}</p>

                    <h1>Data Table</h1>
                    <table>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>포인트</th>
                            <th>상태</th>
                        </tr>
                        </thead>
                        <tbody>
                        {dataList.map((item, index) => (
                            <tr key={item.id}>
                                <td>{index+1}</td>
                                <td>{item.history}</td>
                                <td>{item.status}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            ) : (
                <div></div>
            )}
        </div>
    );
}

export default CashConversion;
