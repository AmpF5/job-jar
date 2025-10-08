package org.jobjar.jobjarapi.infrastructure;

import lombok.RequiredArgsConstructor;
import org.jobjar.jobjarapi.adapters.JustJoinItAdapter;
import org.jobjar.jobjarapi.adapters.TheProtocolAdapter;
import org.jobjar.jobjarapi.persistance.services.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestApi {
    private final JustJoinItAdapter justJoinItAdapter;
    private final TheProtocolAdapter theProtocolAdapter;
    private final OfferService offerService;

    @GetMapping()
    public void Test() {
//        var jjitOffers = justJoinItConnector.getOffers();
//        offerService.handleOffers(jjitOffers);
        var theProtocolOffers = theProtocolAdapter.getOffers();
    }
}
