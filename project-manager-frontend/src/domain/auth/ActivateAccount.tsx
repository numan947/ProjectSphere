import {
  Button,
  HStack,
  PinInput,
  PinInputField,
  SimpleGrid,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import SendCodeModal from "./SendCodeModal";
import useAuthStore from "./store/AuthStore";
import { Navigate } from "react-router-dom";
import { useRequestActivationCode } from "./hooks/useRequestActivationCode";
import useActivateAccount from "./hooks/useActivateAccount";

const ActivateAccount = () => {
  const { token } = useAuthStore();
  if (token) {
    return <Navigate to="/home" />;
  }

  const { isOpen, onClose, onOpen } = useDisclosure();

  // for resending activation code
  const { mutate: resendActivationCode } = useRequestActivationCode();

  const {
    mutate: activateAccount,
    isPending,
    isSuccess,
    isError,
  } = useActivateAccount();

  const submitPin = (pin: string) => {
    activateAccount(pin);
  };

  return (
    <SimpleGrid
      columns={1}
      spacing={4}
      padding={4}
      justifyItems="center"
      justifyContent="center"
    >
      <Text fontSize="4xl" fontWeight="bold">
        Activate Account
      </Text>
      <Text fontSize="xl">
        An activation code has been sent to your email. Please check your inbox
        and enter the code below.
      </Text>
      {isError && (
        <Text fontSize="xl" color="red">
          Invalid activation code
        </Text>
      )}

      <HStack>
        <PinInput
          otp
          type="number"
          onComplete={submitPin}
          isDisabled={isPending || isSuccess}
        >
          <PinInputField />
          <PinInputField />
          <PinInputField />
          <PinInputField />
          <PinInputField />
          <PinInputField />
        </PinInput>
      </HStack>

      <Text fontSize="xl">Didn't receive the code?</Text>
      <>
        {" "}
        <Button
          fontSize="xl"
          variant="link"
          colorScheme="green"
          onClick={onOpen}
        >
          Resend code
        </Button>
        <SendCodeModal
          isOpen={isOpen}
          onSubmit={(email: string) => {
            resendActivationCode(email);
          }}
          onClose={onClose}
          headerText="Resend Activation Code"
          bodyText="Are you sure you want to resend the activation code?"
        />
      </>
    </SimpleGrid>
  );
};

export default ActivateAccount;
