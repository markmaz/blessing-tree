package com.blessingtree.service;

import com.blessingtree.dto.EmailDTO;
import com.blessingtree.model.Address;
import com.blessingtree.model.Sponsor;
import com.blessingtree.model.SponsorYear;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static final int FIRST_NAME = 0;
    public static final int LAST_NAME = 1;
    public static final int ADDRESS = 2;
    public static final int CITY = 3;
    public static final int STATE = 4;
    public static final int ZIP = 5;
    public static final int EMAIL = 6;
    public static final int PHONE = 7;
    public static final int BEST_TIME_TO_CALL = 8;
    public static final int HAS_SPONSORED_PREVIOUSLY = 9;
    public static final int NUMBER_OF_CHILDREN = 10;
    public static final int CHILD_AGE_PREFERENCE = 11;
    public static final int GENDER_PREFERENCE = 12;
    public static final int HEAR_ABOUT_US = 13;
    public static final int WANT_TO_VOLUNTEER = 14;

    private static final String[] EMAIL_VALUES = {"*First Name:*", "*Last Name:*", "*Address:*", "*City:*", "*State:*",
            "*Zip:*", "*Email:*", "*Phone Number:*", "*Best Time To Call:*",
            "*Have you sponsored a child/children through The Blessing Tree Program before?:*",
            "*This year, each parent has indicated one wish / per child. How many " +
                    "children would you like to sponsor this year?:*",
            "*Do you have an age preference for the child that you would like to " +
                    "sponsor?:*",
            "*Do you have a preference on the gender of the child that you would like to " +
                    "sponsor?:*",
            "*How did you hear about St. Mary Magdalene’s Blessing Tree Program?:*",
            "*Would you be interested in becoming a volunteer of Magdalene House Social " +
                    "Services?:*"};
    private static final String EMAIL_SPLIT = "\\*Blessing Tree Sponsor <https://st-mm.com/blessing-tree-sponsor-survey>\\*";

    public static void main(String[] args){
        EmailDTO emailDTO = getEmail();
        parseBodyAndCreateSponsor(emailDTO);
    }

    public static EmailDTO getEmail(){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setBody("<center><table align=3D\"center\" border=3D\"0\" cellpadding=3D\"0\" cellspacing=\n" +
                "=3D\"0\" height=3D\"100%\" width=3D\"100%\" id=3D\"bodyTable\" style=3D\"border-coll=\n" +
                "apse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-ad=\n" +
                "just: 100%;-webkit-text-size-adjust: 100%;height: 100%;margin: 0;padding: 0=\n" +
                ";width: 100%;background-color: #f1f4f5;\"><tbody><tr><td align=3D\"center\" va=\n" +
                "lign=3D\"top\" id=3D\"bodyCell\" style=3D\"mso-line-height-rule: exactly;-ms-tex=\n" +
                "t-size-adjust: 100%;-webkit-text-size-adjust: 100%;height: 100%;margin: 0;p=\n" +
                "adding: 10px;width: 100%;border-top: 0;\"><!--[if (gte mso 9)|(IE)]><table a=\n" +
                "lign=3D'center' border=3D'0' cellspacing=3D'0' cellpadding=3D'0' width=3D'6=\n" +
                "00' style=3D'width:600px;'><tr><td align=3D'center' valign=3D'top' width=3D=\n" +
                "'600' style=3D'width:600px;'><![endif]--><table border=3D\"0\" cellpadding=3D=\n" +
                "\"0\" cellspacing=3D\"0\" width=3D\"100%\" class=3D\"templateContainer\" style=3D\"b=\n" +
                "order-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-te=\n" +
                "xt-size-adjust: 100%;-webkit-text-size-adjust: 100%;border: 0;max-width: 60=\n" +
                "0px !important;\"><tbody><tr><td valign=3D\"top\" id=3D\"templateHeader\" style=\n" +
                "=3D\"background:#f1f4f5 none no-repeat center/cover;mso-line-height-rule: ex=\n" +
                "actly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-=\n" +
                "color: #f1f4f5;background-image: none;background-repeat: no-repeat;backgrou=\n" +
                "nd-position: center;background-size: cover;border-top: 0;border-bottom: 0;p=\n" +
                "adding-top: 9px;padding-bottom: 0px;\"><table border=3D\"0\" cellpadding=3D\"0\"=\n" +
                " cellspacing=3D\"0\" width=3D\"100%\" class=3D\"mcnTextBlock\" style=3D\"min-width=\n" +
                ": 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0p=\n" +
                "t;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><tbody class=\n" +
                "=3D\"mcnTextBlockOuter\"><tr><td valign=3D\"top\" class=3D\"mcnTextBlockInner\" s=\n" +
                "tyle=3D\"padding-top: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust=\n" +
                ": 100%;-webkit-text-size-adjust: 100%;\"><!--[if mso]><table align=3D'left' =\n" +
                "border=3D'0' cellspacing=3D'0' cellpadding=3D'0' width=3D'100%' style=3D'wi=\n" +
                "dth:100%;'><tr><![endif]--><!--[if mso]><td valign=3D'top' width=3D'600' st=\n" +
                "yle=3D'width:600px;'><![endif]--><table align=3D\"left\" border=3D\"0\" cellpad=\n" +
                "ding=3D\"0\" cellspacing=3D\"0\" style=3D\"max-width: 100%;min-width: 100%;borde=\n" +
                "r-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-s=\n" +
                "ize-adjust: 100%;-webkit-text-size-adjust: 100%;\" width=3D\"100%\" class=3D\"m=\n" +
                "cnTextContentContainer\"><tbody><tr><td valign=3D\"top\" class=3D\"mcnTextConte=\n" +
                "nt\" style=3D\"padding-top: 0;padding-right: 18px;padding-bottom: 9px;padding=\n" +
                "-left: 18px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webki=\n" +
                "t-text-size-adjust: 100%;word-break: break-word;color: #202020;font-family:=\n" +
                " Helvetica;font-size: 16px;line-height: 150%;text-align: left;\"><div style=\n" +
                "=3D\"text-align: center;\"><span style=3D\"font-size:14px\"><strong>DO NOT REPL=\n" +
                "Y TO THIS E-MAIL.</strong> This e-mail was automatically generated by a for=\n" +
                "m on the <strong>St. Mary Magdalene Catholic Community website</strong> (po=\n" +
                "wered by <a href=3D\"https://www.ecatholic.com/?referral=3Dmarketing#oid=3D1=\n" +
                "008_2_CMSemail\" target=3D\"_blank\" style=3D\"mso-line-height-rule: exactly;-m=\n" +
                "s-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #e87722;font=\n" +
                "-weight: normal;text-decoration: none;mso-line-height-rule: exactly;-ms-tex=\n" +
                "t-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #e87722;font-weig=\n" +
                "ht: normal;text-decoration: underline;\">eCatholic</a>).</span><br />&#xa0;<=\n" +
                "/div></td></tr></tbody></table><!--[if mso]></td><![endif]--><!--[if mso]><=\n" +
                "/tr></table><![endif]--></td></tr></tbody></table></td></tr><tr><td valign=\n" +
                "=3D\"top\" id=3D\"templateBody\" style=3D\"background:#ffffff none no-repeat cen=\n" +
                "ter/cover;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-=\n" +
                "text-size-adjust: 100%;background-color: #ffffff;background-image: none;bac=\n" +
                "kground-repeat: no-repeat;background-position: center;background-size: cove=\n" +
                "r;border-top: 0;border-bottom: 3px solid #EAEAEA;padding-top: 35px;padding-=\n" +
                "bottom: 20px;\"><table border=3D\"0\" cellpadding=3D\"0\" cellspacing=3D\"0\" widt=\n" +
                "h=3D\"100%\" class=3D\"mcnTextBlock\" style=3D\"min-width: 100%;border-collapse:=\n" +
                " collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust:=\n" +
                " 100%;-webkit-text-size-adjust: 100%;\"><tbody class=3D\"mcnTextBlockOuter\"><=\n" +
                "tr><td valign=3D\"top\" class=3D\"mcnTextBlockInner\" style=3D\"padding-top: 9px=\n" +
                ";mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size=\n" +
                "-adjust: 100%;\"><!--[if mso]><table align=3D'left' border=3D'0' cellspacing=\n" +
                "=3D'0' cellpadding=3D'0' width=3D'100%' style=3D'width:100%;'><tr><![endif]=\n" +
                "--><!--[if mso]><td valign=3D'top' width=3D'600' style=3D'width:600px;'><![=\n" +
                "endif]--><table align=3D\"left\" border=3D\"0\" cellpadding=3D\"0\" cellspacing=\n" +
                "=3D\"0\" style=3D\"max-width: 100%;min-width: 100%;border-collapse: collapse;m=\n" +
                "so-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webk=\n" +
                "it-text-size-adjust: 100%;\" width=3D\"100%\" class=3D\"mcnTextContentContainer=\n" +
                "\"><tbody><tr><td valign=3D\"top\" class=3D\"mcnTextContent\" style=3D\"padding: =\n" +
                "0px 18px 9px;font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, Ver=\n" +
                "dana, sans-serif;font-size: 16px;line-height: 150%;mso-line-height-rule: ex=\n" +
                "actly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;word-break:=\n" +
                " break-word;color: #646464;text-align: left;\"><p style=3D\"text-align: cente=\n" +
                "r;margin: 0px 0px 9px;\"><strong><a href=3D\"https://st-mm.com/blessing-tree-=\n" +
                "sponsor-survey\" style=3D\"mso-line-height-rule: exactly;-ms-text-size-adjust=\n" +
                ": 100%;-webkit-text-size-adjust: 100%;color: #e87722;font-weight: normal;te=\n" +
                "xt-decoration: none;mso-line-height-rule: exactly;-ms-text-size-adjust: 100=\n" +
                "%;-webkit-text-size-adjust: 100%;color: #e87722;font-weight: normal;text-de=\n" +
                "coration: none;\">Blessing Tree Sponsor</a></strong></p><br /><strong>First =\n" +
                "Name:</strong> Ronald<br /><strong>Last Name:</strong> Cecil<br /><strong>A=\n" +
                "ddress:</strong> 3531 Orange Grove Dr<br /><strong>City:</strong> Houston<b=\n" +
                "r /><strong>State:</strong> TX<br /><strong>Zip:</strong> 77039<br /><stron=\n" +
                "g>Email:</strong> rcecil54@gmail.com<br /><strong>Phone Number:</strong> 28=\n" +
                "15071978<br /><strong>Best Time To Call:</strong> <br /><strong>Have you sp=\n" +
                "onsored a child/children through The Blessing Tree Program before?:</strong=\n" +
                "> <br /><strong>This year, each parent has indicated one wish / per child. =\n" +
                "How many children would you like to sponsor this year?:</strong> 3<br /><st=\n" +
                "rong>Do you have an age preference for the child that you would like to spo=\n" +
                "nsor?:</strong> <br /><strong>Do you have a preference on the gender of the=\n" +
                " child that you would like to sponsor?:</strong> <br /><strong>How did you =\n" +
                "hear about St. Mary Magdalene=E2=80=99s Blessing Tree Program?:</strong> I/=\n" +
                "my family have participated in it before<br /><strong>Would you be interest=\n" +
                "ed in becoming a volunteer of Magdalene House Social Services?:</strong> No=\n" +
                "t at this time<br /></td></tr></tbody></table><!--[if mso]></td><![endif]--=\n" +
                "><!--[if mso]></tr></table><![endif]--></td></tr></tbody></table><table bor=\n" +
                "der=3D\"0\" cellpadding=3D\"0\" cellspacing=3D\"0\" width=3D\"100%\" class=3D\"mcnTe=\n" +
                "xtBlock\" style=3D\"min-width: 100%;border-collapse: collapse;mso-table-lspac=\n" +
                "e: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-a=\n" +
                "djust: 100%;\"><tbody class=3D\"mcnTextBlockOuter\"><tr><td valign=3D\"top\" cla=\n" +
                "ss=3D\"mcnTextBlockInner\" style=3D\"padding-top: 9px;mso-line-height-rule: ex=\n" +
                "actly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><!--[if m=\n" +
                "so]><table align=3D'left' border=3D'0' cellspacing=3D'0' cellpadding=3D'0' =\n" +
                "width=3D'100%' style=3D'width:100%;'><tr><![endif]--><!--[if mso]><td valig=\n" +
                "n=3D'top' width=3D'600' style=3D'width:600px;'><![endif]--><table align=3D\"=\n" +
                "left\" border=3D\"0\" cellpadding=3D\"0\" cellspacing=3D\"0\" style=3D\"max-width: =\n" +
                "100%;min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-ta=\n" +
                "ble-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"=\n" +
                " width=3D\"100%\" class=3D\"mcnTextContentContainer\"><tbody><tr><td valign=3D\"=\n" +
                "top\" class=3D\"mcnTextContent\" style=3D\"padding: 9px 18px 9px;font-family: &=\n" +
                "quot;Helvetica Neue&quot;, Helvetica, Arial, Verdana, sans-serif;font-size:=\n" +
                " 16px;line-height: 150%;mso-line-height-rule: exactly;-ms-text-size-adjust:=\n" +
                " 100%;-webkit-text-size-adjust: 100%;word-break: break-word;color: #646464;=\n" +
                "text-align: left;\"></td></tr></tbody></table><!--[if mso]></td><![endif]-->=\n" +
                "<!--[if mso]></tr></table><![endif]--></td></tr></tbody></table></td></tr><=\n" +
                "/tbody></table></td></tr><tr><td valign=3D\"top\" id=3D\"templateFooter\" style=\n" +
                "=3D\"background:#f1f4f5 none no-repeat center/cover;mso-line-height-rule: ex=\n" +
                "actly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-=\n" +
                "color: #f1f4f5;background-image: none;background-repeat: no-repeat;backgrou=\n" +
                "nd-position: center;background-size: cover;border-top: 0;border-bottom: 0;p=\n" +
                "adding-top: 25px;padding-bottom: 9px;\"></td></tr></tbody></table><!--[if (g=\n" +
                "te mso 9)|(IE)]></td></tr></table><![endif]--><!-- // END TEMPLATE --></cen=\n" +
                "ter>");
        return emailDTO;
    }
    public static int parseBodyAndCreateSponsor(EmailDTO emailDTO){
        String heads = emailDTO.getBody().trim();
        //String heads = body[1].trim();

        heads = heads.replaceAll("\n", " ");
        heads = heads.replaceAll("\r", "");
        List<String> entityValues = new ArrayList<>();

        for(int i = 0; i < EMAIL_VALUES.length ; i++){
            String out;

            if (i < EMAIL_VALUES.length - 1){
                out = heads.substring(heads.indexOf(EMAIL_VALUES[i]) + EMAIL_VALUES[i].length(), heads.indexOf(EMAIL_VALUES[i + 1])).trim();
            }else{
                out = heads.substring(heads.indexOf(EMAIL_VALUES[i]) + EMAIL_VALUES[i].length(), heads.length()).trim();
            }

            if (out.isBlank() || out.isEmpty()){
                entityValues.add("");
            }else{
                entityValues.add(out.replace("\n", " "));
            }
        }

        Sponsor sponsor = mapSponsor(entityValues);
        System.out.println(sponsor);
        return HttpStatus.SC_OK;
    }

    private static Sponsor mapSponsor(List<String> entityValues) {
        Sponsor sponsor = new Sponsor();
        sponsor.setFirstName(entityValues.get(FIRST_NAME));
        sponsor.setLastName(entityValues.get(LAST_NAME));

        Address address = new Address();
        address.setStreet(entityValues.get(ADDRESS));
        address.setCity(entityValues.get(CITY));
        address.setState(entityValues.get(STATE));
        address.setZip(entityValues.get(ZIP));
        sponsor.setAddress(address);

        sponsor.setEmail(entityValues.get(EMAIL));
        sponsor.setPhone(entityValues.get(PHONE));
        sponsor.setBestTimeToCall(entityValues.get(BEST_TIME_TO_CALL));
        sponsor.setHasSponsoredPreviously(convertToBool(entityValues.get(HAS_SPONSORED_PREVIOUSLY)));

        SponsorYear sponsorYear = new SponsorYear();
        sponsorYear.setNumberOfChildrenSponsored(Long.valueOf(entityValues.get(NUMBER_OF_CHILDREN)));
        sponsorYear.setChildAgePreference(entityValues.get(CHILD_AGE_PREFERENCE));
        sponsorYear.setGenderPreference(entityValues.get(GENDER_PREFERENCE));
        sponsor.getSponsorYear().add(sponsorYear);

        sponsor.setHowDidYouHearAboutUs(entityValues.get(HEAR_ABOUT_US));
        sponsor.setWantToVolunteer(convertToBool(entityValues.get(WANT_TO_VOLUNTEER)));

        return sponsor;
    }

    private static Boolean convertToBool(String s) {
        if(s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")
                || s.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
}
