import axios from 'axios';
import * as apiCalls from './apiCalls';

describe('apiCalls', () => {
    describe('signup', () => {

        it('calls /users', () => {
            const mockSignup = jest.fn();
            axios.post = mockSignup;
            
            apiCalls.postSignup();

            const path = mockSignup.mock.calls[0][0];
            expect(path).toBe('/users');
        });

        it('calls /login', () => {
            const mockSignup = jest.fn();
            axios.post = mockSignup;
            
            apiCalls.login( {username: "test-user", password: "P4ssword"});
        
            const path = mockSignup.mock.calls[0][0];
            expect(path).toBe('/login');
        });
    });
});