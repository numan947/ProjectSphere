import {
  Alert,
  AlertDescription,
  AlertIcon,
  AlertTitle,
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
import { useState } from "react";

const ActivateAccount = () => {
  const { token } = useAuthStore();
  if (token) {
    return <Navigate to="/home" />;
  }

  const { isOpen, onClose, onOpen } = useDisclosure();

  const [codeSent, setCodeSent] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const onSuccessfulActivation = () => {};
  const onErrorHandler = (msg: string) => {
    setErrorMessage(msg);
    console.log(errorMessage);
    setTimeout(() => {
      setErrorMessage("");
    }, 5000);
  };

  // for resending activation code
  const { mutate: resendActivationCode } = useRequestActivationCode(() => {
    setCodeSent(true);
    setTimeout(() => {
      setCodeSent(false);
    }, 5000);
  }, onErrorHandler);

  const {
    mutate: activateAccount,
    isPending,
    isSuccess,
  } = useActivateAccount(onSuccessfulActivation, onErrorHandler);

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
      {
        // if the activation is successful, show success message
        isSuccess && (
          <Alert status="success" justifyContent="center">
            <AlertIcon />
            <AlertTitle>Activation Successful!</AlertTitle>
            <AlertDescription>
              Your account has been activated. Now you can log in.
            </AlertDescription>
          </Alert>
        )
      }

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
          isDisabled={codeSent || isPending || isSuccess}
        >
          Resend code
        </Button>
        {!errorMessage && codeSent && (
          <Alert status="info" justifyContent="center">
            <AlertIcon />
            <AlertTitle>Info</AlertTitle>
            <AlertDescription>
              A new activation code has been sent to your email.
            </AlertDescription>
          </Alert>
        )}
        {errorMessage && (
          <Alert status="error" justifyContent="center">
            <AlertIcon />
            <AlertTitle>Activation Failed!</AlertTitle>
            <AlertDescription>{errorMessage}</AlertDescription>
          </Alert>
        )}
        <SendCodeModal
          isOpen={isOpen}
          onSubmit={(email: string) => {
            resendActivationCode(email);
          }}
          onClose={() => {
            onClose();
          }}
          headerText="Resend Activation Code"
          bodyText="Are you sure you want to resend the activation code?"
        />
      </>
    </SimpleGrid>
  );
};

export default ActivateAccount;
