package com.blessingtree.service;

import com.blessingtree.dto.EmailDTO;
import com.blessingtree.model.Address;
import com.blessingtree.model.Sponsor;
import com.blessingtree.model.SponsorYear;
import com.blessingtree.repository.SponsorRepository;
import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailService extends BaseService{
    public static final String FIRST_NAME = "First Name:";
    public static final String LAST_NAME = "Last Name:";
    public static final String ADDRESS = "Address:";
    public static final String CITY = "City:";
    public static final String STATE = "State:";
    public static final String ZIP = "Zip:";
    public static final String EMAIL = "Email:";
    public static final String PHONE_NUMBER = "Phone Number:";
    public static final String BEST_TIME_TO_CALL = "Best Time To Call:";
    public static final String SPONSORED_PREVIOUSLY = "Have you sponsored a child/children through The Blessing Tree Program before?:";
    public static final String HOW_MANY_CHILDREN = "This year, each parent has indicated one wish / per child. How many children would you like to sponsor this year?:";
    public static final String AGE_PREFERENCE = "Do you have an age preference for the child that you would like to sponsor?:";
    public static final String GENDER_PREFERENCE = "Do you have a preference on the gender of the child that you would like to sponsor?:";
    public static final String HEAR_ABOUT_US = "How did you hear about St. Mary Magdalene’s Blessing Tree Program?:";
    public static final String WANT_TO_VOLUNTEER = "Would you be interested in becoming a volunteer of Magdalene House Social Services?:";
    private final SponsorRepository sponsorRepository;
    private static final String[] EMAIL_VALUES = {"*First Name:*", "*Last Name:*", "*Address:*", "*City:*", "*State:*",
            "*Zip:*", "*Email:*", "*Phone Number:*", "*Best Time To Call:*",
            "*Have you sponsored a child/children through The Blessing Tree Program " +
                    "before?:*",
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

    public EmailService(@Autowired ModelMapper mapper,
                        @Autowired SponsorRepository sponsorRepository){
        super(mapper);
        this.sponsorRepository = sponsorRepository;
    }

    public int parseBodyAndCreateSponsor(EmailDTO emailDTO){
        Document document = Jsoup.parse(emailDTO.getBody());
        Elements strongTags = document.select("strong");
        Map<String, String> map = new HashMap<>();

        for (Element strong : strongTags) {
            String strongText = strong.text().trim();
            String nextSibling = strong.nextSibling() != null ? Objects.requireNonNull(strong.nextSibling()).toString().trim() : "";

            nextSibling = nextSibling.replaceAll("<.*?>", "").trim();
            map.put(strongText, nextSibling);
        }
        sponsorRepository.save(mapSponsor(map));
        return HttpStatus.SC_OK;
    }

    private Sponsor mapSponsor(Map<String, String> map) {
        Sponsor sponsor = new Sponsor();
        sponsor.setFirstName(map.get(FIRST_NAME));
        sponsor.setLastName(map.get(LAST_NAME));

        Address address = new Address();
        address.setAddress(map.get(ADDRESS));
        address.setCity(map.get(CITY));
        address.setState(map.get(STATE));
        address.setZip(map.get(ZIP));
        sponsor.setAddress(address);

        sponsor.setEmail(map.get(EMAIL));
        sponsor.setPhone(map.get(PHONE_NUMBER));
        sponsor.setBestTimeToCall(BEST_TIME_TO_CALL);
        sponsor.setHasSponsoredPreviously(convertToBool(map.get(SPONSORED_PREVIOUSLY)));

        SponsorYear sponsorYear = new SponsorYear();
        sponsorYear.setNumberOfChildrenSponsored(Long.valueOf(map.get(HOW_MANY_CHILDREN)));
        sponsorYear.setChildAgePreference(map.get(AGE_PREFERENCE));
        sponsorYear.setGenderPreference(map.get(GENDER_PREFERENCE));
        sponsor.getSponsorYear().add(sponsorYear);

        sponsor.setHowDidYouHearAboutUs(map.get(HEAR_ABOUT_US));
        sponsor.setWantToVolunteer(convertToBool(map.get(WANT_TO_VOLUNTEER)));

        return sponsor;
    }

    private Boolean convertToBool(String s) {
        if(s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y")
                || s.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
}
