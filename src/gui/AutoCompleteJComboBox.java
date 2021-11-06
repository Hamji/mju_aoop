package gui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class AutoCompleteJComboBox extends JComboBox {
    private JTextComponent editor;
    private String filteredCountry;
    private AutoCompleteDocument autoCompleteDocument;
    private final String[] countries = new String[]{
            "가나", "가봉", "가이아나", "감비아", "건지", "과들루프", "과테말라", "괌", "교황청", "그레나다", "그리스", "그린란드", "기니", "기니비사우", "나미비아", "나우루", "나이지리아", "남수단", "남아프리카공화국", "네덜란드", "네덜란드령 안틸레스", "네팔", "노르웨이", "뉴 칼레도니아", "뉴질랜드", "니우에", "니제르", "니카라과", "덴마크", "도미니카공화국", "도미니 카연방", "독일", "동티모르", "라오스", "라이베리아", "라트비아", "러시아", "레바논", "레소토", "레위니옹", "루마니아", "룩셈부르크", "르완다", "리비아", "리투아니아", "리히텐슈타인", "마다가스카르", "마르티니크", "마셜제도", "마요트", "마이크로네시아연방", "말라위", "말레이시아", "말리", "맨 섬", "멕시코", "모나코", "모로코", "모리셔스", "모리타니아", "모잠 비크", "몬테네그로", "몬트세랫", "몰도바", "몰디브", "몰타", "몽골", "미국", "미얀마", "바누아투", "바레인", "바베이도스", "바하마", "방글라데시", "버뮤다", "베냉", "베네수엘라", "베트남", "벨기에", "벨라루스", "벨리즈", "보스니아헤르체고비나", "보츠와나", "볼리비아", "부룬디", "부르키나파소", "부탄", "북마리아나 군도", "북마케도니아", "불가리아", "브라질", "브루나이", "사모아", "사우디아라비아", "사이프러스", "사하라 아랍민주공화국(서부사하라)", "산마리노", "상투메프린시페", "생피에르·미클롱", "세네갈", "세르비아", "세이셸", "세인트 루시아", "세인트 빈센트 그레나딘", "세인트 키츠 네비스", "세인트 헬레나 섬", "소말리아", "솔로몬제도", "수단", "수리남", "스리랑카", "스웨덴", "스위스", "스페인", "슬로바키아", "슬로베니아", "시리아", "시에라리온", "싱가포르", "아랍에미리트", "아루바", "아르메니아", "아르헨티나", "아이슬란드", "아이티", "아일랜드", "아제르바이잔", "아프가니스탄", "안도라", "알바니아", "알제리", "앙골라", "앤티가바부다", "앵귈라", "에리트레아", "에스와티니", "에스토니아", "에콰도르", "에티오피아", "엘살바도르", "영국", "영국령 남극지역", "영국령 버진  아일랜드", "영국령 인도양 제도", "예멘", "오만", "오스트리아", "온두라스", "왈리스·퓌튀나", "요르단", "우간다", "우루과 이", "우즈베키스탄", "우크라이나", "이라크", "이란", "이스라엘", "이집트", "이탈리아", "인도", "인도네시아", "일본", "자메이카", "잠비아", "저지", "적도기니", "조지아", "중국", "중앙아프리카공화국", "지부티", "지브롤터", "짐바브웨", "차드", "체코", "칠레", "카메룬", "카보베르데", "카자흐스탄", "카타르", "캄보디아", "캐나다", "케냐", "케이맨제도", "코모로", "코소보", "코스타리카", "코트디부아르", "콜롬비아", "콩고공화국", "콩고민주공화국(DR콩고)", "쿠바", "쿠웨이트", "쿡제도", "크로아티아", "키르기즈", "키리바시", "타이완", "타지키스탄", "탄자니아", "태국", "터크스·케이커스 제도", "터키", "토고", "통가", "투르크메니스탄", "투발루", "튀니지", "트리니다드토바고", "파나마", "파라과이", "파키스탄", "파푸아뉴기니", "팔라우", "팔레스타인", "페루", "포르투갈", "폴란드", "프랑스", "프랑스령 기아나", "프랑스령 폴리네시아", "피지", "핀란드", "필리핀", "핏케언 섬", "헝가리", "호주", "홍콩"
    };

    class AutoCompleteDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offs, str, a);
            String content = getText(0, getLength());
            filteredCountry = lookupItem(content);

            if(filteredCountry == null) {
                filteredCountry = getText(0, getLength());
            }

            setSelectedItem(filteredCountry);
            editor.setText(content);
        }

        public void autoComplete() {
            editor.setText(filteredCountry);
        }
    }

    public AutoCompleteJComboBox() {
        for(String country: countries) addItem(country);

        setEditable(true);
        editor = (JTextComponent) getEditor().getEditorComponent();
        autoCompleteDocument = new AutoCompleteDocument();
        editor.setDocument(autoCompleteDocument);

        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    autoCompleteDocument.autoComplete();
                    new ResultFrame(filteredCountry);
                }
                setPopupVisible(true);
            }
        });


    }

    public String lookupItem(String text) {
        for(String country: countries) {
            if(country.startsWith(text)) return country;
        }
        return null;
    }

}




