CREATE TABLE users (
                       id BINARY(16) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE wallets (
                         id BINARY(16) PRIMARY KEY,
                         user_id BINARY(16) NOT NULL UNIQUE,
                         balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
                         version BIGINT NOT NULL DEFAULT 0,
                         CONSTRAINT fk_wallets_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transactions (
                              id BINARY(16) PRIMARY KEY,
                              sender_wallet_id BINARY(16) NOT NULL,
                              receiver_wallet_id BINARY(16) NOT NULL,
                              amount DECIMAL(15, 2) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_transactions_sender FOREIGN KEY (sender_wallet_id) REFERENCES wallets(id),
                              CONSTRAINT fk_transactions_receiver FOREIGN KEY (receiver_wallet_id) REFERENCES wallets(id)
);