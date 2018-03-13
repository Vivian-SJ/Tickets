package tickets.security;

public class TokenDetailImpl implements TokenDetail{
    private final int memberId;

    public TokenDetailImpl(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public int getMemberId() {
        return this.memberId;
    }
}
