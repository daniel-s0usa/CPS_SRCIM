package Product;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;



public class offerTransport extends Agent {
    @Override
    protected void setup(){
        //add a behaviour based on an agent
        this.addBehaviour(new responder(this, MessageTemplate.MatchPerformative(ACLMessage.CFP)));
    }

    private class responder extends ContractNetResponder {
        public responder(Agent a, MessageTemplate mt){
            super(a, mt);
        }

        @Override
        protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException{
            System.out.println(myAgent.getLocalName() + ": Processing CFP message");
            ACLMessage msg = cfp.createReply();
            //Set a performative which is, in this case, Make a propose ACLMessage.PROPOSE
            msg.setPerformative(ACLMessage.PROPOSE);
            //Writes the :content slot
            msg.setContent("My Proposal value");
            return msg;
        }

        @Override
        protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException{
            System.out.println(myAgent.getLocalName() + ": Preparing result of CFP");
            block(5000);
            ACLMessage msg = cfp.createReply();
            //Set a performative which is, in this case, ACLMessage.INFORM
            msg.setPerformative(ACLMessage.INFORM);
            return msg;
        }

    }
}
