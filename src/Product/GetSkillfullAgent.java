package Product;

import Utilities.DFInteraction;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class GetSkillfullAgent extends SimpleBehaviour {

    Agent a;
    String Skill;
    private boolean finished = false;
    public GetSkillfullAgent (Agent a, String Skill) {
        this.a = a;
        this.Skill = Skill;
    }

    @Override
    public void action() {
        ACLMessage cfp = getCompetentAgents();
        a.addBehaviour(new SkillNegotiation(a,cfp));

        finished = true; //ISTO DEVE CAUSAR PROBLEMAS CASO TENHA MAIS CENAS NA SEQ DEFINIDA NO PRODUCTAGENT!!

    }

    @Override
    public boolean done() {
        return finished;
    }

    // Returns a list of agents registered in the DF with a given skill
    private ACLMessage getCompetentAgents () {
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        DFAgentDescription [] SkillfulAgents = null;

        try {
            SkillfulAgents = DFInteraction.SearchInDFByName(Skill,a);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        if ( SkillfulAgents.length != 0 ) {
            System.out.println("List of agents that can execute the skill " + this.Skill + " : ");
            for (int i = 0; i < SkillfulAgents.length; i++) {
                System.out.println(SkillfulAgents[i].getName());
                cfp.addReceiver(SkillfulAgents[i].getName());
            }
        } else {
            System.out.println("There are no agents with the following skill: " + this.Skill);
            // we'll have to abort in this case
        }
        return cfp;
    }


}
