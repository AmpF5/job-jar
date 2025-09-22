package org.jobjar.jobjarapi.adapters.listeners;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class SkillSnapshotListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(Long offerId) {
        // TODO: Handle adding SkillSnapshot
        System.out.println(offerId);
    }
}
