package com.platonicideal;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platonicideal.plugins.activecampaign.contact.Contact;
import com.platonicideal.plugins.activecampaign.contact.Domain;
import com.platonicideal.plugins.activecampaign.contact.DomainRepository;
import com.platonicideal.plugins.activecampaign.contact.Email;
import com.platonicideal.plugins.activecampaign.events.bounce.Bounce;
import com.platonicideal.plugins.activecampaign.events.bounce.BounceEvent;
import com.platonicideal.plugins.activecampaign.summary.CampaignSummary;
import com.platonicideal.plugins.activecampaign.summary.CampaignSummaryRepository;
import com.platonicideal.plugins.activecampaign.summary.DomainActivity;

@Service
public class CampaignActivityTestService {

    private static final Logger LOG = LoggerFactory.getLogger(CampaignActivityTestService.class);

    private final DomainRepository domainRepository;
    private final CampaignSummaryRepository campaignActivityRepository;

    @Autowired
    public CampaignActivityTestService(DomainRepository domainRepository,
            CampaignSummaryRepository campaignActivityRepository) {
        this.domainRepository = domainRepository;
        this.campaignActivityRepository = campaignActivityRepository;
    }

    @Transactional
    public void doStuff() {
        Domain gmail = domainRepository.save(new Domain("gmail.com"));

        CampaignSummary newCampaign = new CampaignSummary();
        newCampaign.setActiveCampaignId(101L);
        CampaignSummary campaignSummary = campaignActivityRepository.save(newCampaign);

        DomainActivity domainActivity = new DomainActivity();
        domainActivity.setDomain(gmail);
        domainActivity.setClicks(1L);
        domainActivity.setOpens(10L);
        Bounce gmailBounce = new Bounce();
        gmailBounce.setCode("bounce code");
        gmailBounce.setDescription("bounce description");
        gmailBounce.setType("bounce type");
        Contact gmailContact = new Contact(101L, new Email("bob".hashCode(), gmail));
        domainActivity.getBounceEvents()
                .add(new BounceEvent(new Date(1001L), "initated by", 100L, 1000L, gmailBounce, gmailContact));

        campaignSummary.getActivityByDomain().put(gmail, domainActivity);
        CampaignSummary savedSummary = campaignActivityRepository.save(campaignSummary);

        Optional<CampaignSummary> loaded = campaignActivityRepository.findById(savedSummary.getId());
        LOG.info(loaded.toString());
    }

}
